package org.ypf.generic.dependency;

/**
 * id 生成器
 * 保证一个jvm内不会重复
 * 线程不安全的
 * 原理：64 long 类型分为3段
 * 低位时间戳
 * 中位7位 代表线程id 大于secondSeedMax 后共享一个同步生成器
 * 高位-1 最大为firstSeedMax
 *
 * @date: 2022/5/20 14:35
 */
public class Id3sGen implements IdGen {
    public final static long firstSeedMax = 32767;
    /**
     * 线程id最大值
     */
    public final static long secondSeedMax = 127;
    long firstSeed;
    long t0;

    Id3sGen() {
    }

    public static long id(long firstSeed, long secondSeed, long thirdSeed) {
        return firstSeed << 48 | secondSeed << 41 | thirdSeed;
    }

    public static IdGen idGen() {
        return new IdSyncGenProxy();
    }

    /**
     * 获取当地线程中的id生成器
     * 本地的线程
     *
     * @return
     */
    public static IdGen getCurrentThreadIdGen() {
        long id = Thread.currentThread().getId();
        if (id > secondSeedMax || id <= 0)
            return IdSyncGen.idSyncGen;
        return new Id3sGen();
    }

    public long getSecondSeed() {
        long id = Thread.currentThread().getId();
        if (id > secondSeedMax)
            throw new RuntimeException("Thread Id is illegality");
        return id;
    }

    public long id() {
        long re;
        long secondSeed = getSecondSeed();
        long thirdSeed = System.currentTimeMillis();
        if (firstSeed > firstSeedMax && thirdSeed == t0) {
            try {
                Thread.sleep(1);
                firstSeed = 0;
                thirdSeed = System.currentTimeMillis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        re = id(firstSeed, secondSeed, thirdSeed);
        firstSeed++;
        t0 = thirdSeed;
        return re;
    }


    static class IdSyncGenProxy implements IdGen {
        final static ThreadLocal<IdGen> threadLocal = new ThreadLocal<IdGen>();

        @Override
        public long id() {
            IdGen idGen = threadLocal.get();
            if (idGen == null) {
                threadLocal.set(getCurrentThreadIdGen());
            }
            return threadLocal.get().id();
        }
    }

    static class IdSyncGen extends Id3sGen {
        static final Id3sGen idSyncGen = new IdSyncGen();

        public long getSecondSeed() {
            return 0L;
        }

        @Override
        public synchronized long id() {
            return super.id();
        }
    }


}
