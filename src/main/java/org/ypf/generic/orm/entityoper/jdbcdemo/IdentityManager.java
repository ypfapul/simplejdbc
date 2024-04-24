package org.ypf.generic.orm.entityoper.jdbcdemo;

import org.ypf.generic.orm.entityoper.EntityMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 本地id自增管理
 *
 * @date: 2022/7/6 15:24
 */
class IdentityManager {
    final static Map<EntityMapper, AtomicLong> idmap = new ConcurrentHashMap<>();

    /**
     * 初始化
     *
     * @param entityMapper
     * @param value
     */
    static void init(EntityMapper entityMapper, Long value) {
        if (!init(entityMapper)) {
            idmap.put(entityMapper, new AtomicLong(value));
        }

    }

    /**
     * 是否初始化
     *
     * @param entityMapper
     * @return
     */
    static boolean init(EntityMapper entityMapper) {
        return idmap.get(entityMapper) != null;
    }

    static Long next(EntityMapper entityMapper) {
        return idmap.get(entityMapper).incrementAndGet();
    }


}
