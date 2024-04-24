package test;

import org.apache.commons.dbcp.BasicDataSource;
import org.ypf.generic.orm.JdbcTemplete;
import org.ypf.generic.orm.SessionFactory;
import org.ypf.generic.orm.entityoper.DaoEntity;
import org.ypf.generic.orm.entityoper.EntityMapper;
import org.ypf.generic.orm.entityoper.EntityMapperRepository;
import org.ypf.generic.orm.entityoper.jdbcdemo.EntityTemplateImpl;
import org.ypf.generic.orm.entityoper.jdbcdemo.ObjectTempleteImpl;


import java.text.SimpleDateFormat;


public class DssoasNpTaskTest implements DaoEntity {

    static BasicDataSource bascis = new BasicDataSource();

    static {
        bascis.setUrl("");
        bascis.setUsername("xxxx");
        bascis.setPassword("xxxx");
        bascis.setDriverClassName("xxx");
    }


    public static void main(String[] args) {
        //Conditions x = Conditions.FULL;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss:sss");
        EntityMapper entityMapperDssoasNpTask = EntityMapperRepository.get(DssoasNpTask.class);
        SessionFactory sessionFactory = new SessionFactory(bascis);
        JdbcTemplete jdbcTemplete = new JdbcTemplete(sessionFactory);
        ObjectTempleteImpl objectTemplete = new ObjectTempleteImpl(jdbcTemplete);
        EntityTemplateImpl entityTemplate = new EntityTemplateImpl(objectTemplete);
//		DssoasNpTask dssoasNpTask = new DssoasNpTask();
//		dssoasNpTask.setTaskid(Id3sGen.idGen().id());
//		dssoasNpTask.setTaskname("abv");
//		dssoasNpTask.setMdftime(new Date());
//		dssoasNpTask.setTaskpri(12);
//		dssoasNpTask.setTaskdes("abv");
//		entityTemplate.save(dssoasNpTask);
        //CFBindingConditions c5 = new CFBindingConditions(entityMapper.cFBindings().get(0), Oper.MT, 1);
//		 dssoasNpTask = objectTemplete.get(entityMapperDssoasNpTask.getColumFieldBinding("taskname").like("renwuss"), entityMapperDssoasNpTask);
//		System.out.println(dssoasNpTask);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss:sss");
//		System.out.println(simpleDateFormat.format(dssoasNpTask.getMdftime()));
//		List<DssoasNpTask> listDssoasNpTask =  objectTemplete.getList(entityMapperDssoasNpTask.getColumFieldBinding("taskname").like("abv"), entityMapperDssoasNpTask);
//		for (DssoasNpTask dssoasNpTask1 :listDssoasNpTask){
//			System.out.println("========================");
//			System.out.println(dssoasNpTask1);
//			System.out.println(simpleDateFormat.format(dssoasNpTask1.getMdftime()));
//		}
//
//		ColumFieldBinding columFieldBinding = entityMapperDssoasNpTask.getColumFieldBinding("taskpri");
//		Conditions conditions0 = columFieldBinding.le(10).and(columFieldBinding.mt(1));
//		conditions0 = conditions0.or(entityMapperDssoasNpTask.getColumFieldBinding("taskdes").like("abv"));
//        System.out.println(conditions0.express());
//		conditions0 = conditions0.reverse();
//        System.out.println(conditions0.express());
//        conditions0 = conditions0.reverse();
//        System.out.println(conditions0.express());
//		List<DssoasNpTask> listDssoasNpTask =  objectTemplete.getList(conditions0, entityMapperDssoasNpTask);
//		for (DssoasNpTask dssoasNpTask1 :listDssoasNpTask){
//			System.out.println("========================");
//			System.out.println(dssoasNpTask1);
//			System.out.println(simpleDateFormat.format(dssoasNpTask1.getMdftime()));
//		}
//
//		ColumFieldBinding columFieldBinding1 = entityMapperDssoasNpTask.getColumFieldBinding("mdftime");
//
//		 listDssoasNpTask =  objectTemplete.getList(columFieldBinding1.mt(listDssoasNpTask.get(0).getMdftime()), entityMapperDssoasNpTask);
//		for (DssoasNpTask dssoasNpTask1 :listDssoasNpTask){
//			System.out.println("===========RRRR============");
//			System.out.println(dssoasNpTask1);
//			System.out.println(simpleDateFormat.format(dssoasNpTask1.getMdftime()));
//		}
        // entityMapperDssoasNpTask.mirror();
//        Table table = entityMapperDssoasNpTask.table();
//        table.mirror();
//        entityMapperDssoasNpTask.javaClass().mirror();

//       DssoasNpTask dssoasNpTask = entityTemplate.get(848278351215089l, DssoasNpTask.class);
//        System.out.println(dssoasNpTask);
//        System.out.println(simpleDateFormat.format(dssoasNpTask.getMdftime()));
//        dssoasNpTask.setTasktype(44433);
//        dssoasNpTask.setDeltime(new Date());
//        dssoasNpTask.setMdftime(new Date());
//        dssoasNpTask.setTaskdes("yingxiong");
//        //指定更新
//        entityTemplate.updateByBinds(dssoasNpTask,"tasktype","taskdes");
        //entityTemplate.update(dssoasNpTask);
//        entityTemplate.delete(dssoasNpTask);


        //批量添加数据
//      List<DssoasNpTask> listDssoasNpTask = new ArrayList<>();
//        LocalDateTime be = LocalDateTime.now();
//        for(int i=0;i<100000;i++){
//            DssoasNpTask dssoasNpTask = new DssoasNpTask();
//            dssoasNpTask.setTaskid(Id3sGen.idGen().id());
//            dssoasNpTask.setTaskname("abv");
//            dssoasNpTask.setMdftime(new Date());
//            dssoasNpTask.setTaskpri(12);
//            dssoasNpTask.setTaskdes("abv");
//            listDssoasNpTask.add(dssoasNpTask);
//
//        }
//        entityTemplate.batchSave(listDssoasNpTask);
//        LocalDateTime en = LocalDateTime.now();
//        long runtime = ChronoUnit.SECONDS.between(be, en);
//        System.out.println("runtime {} min "+runtime);

        // 非批量提交
//        LocalDateTime be = LocalDateTime.now();
//        for (int i = 0; i < 10000; i++) {
//            System.out.println(i);
//            DssoasNpTask dssoasNpTask = new DssoasNpTask();
//            dssoasNpTask.setTaskid(Id3sGen.idGen().id());
//            dssoasNpTask.setTaskname("abv");
//            dssoasNpTask.setMdftime(new Date());
//            dssoasNpTask.setTaskpri(12);
//            dssoasNpTask.setTaskdes("abv");
//            entityTemplate.save(dssoasNpTask);
//
//        }
//
//        LocalDateTime en = LocalDateTime.now();
//        long runtime = ChronoUnit.SECONDS.between(be, en);
//        System.out.println("runtime {} min " + runtime);

//        DssoasNpTask dssoasNpTask = new DssoasNpTask();
//        dssoasNpTask.setTaskid(Id3sGen.idGen().id());
//        dssoasNpTask.setTaskname("abv");
//        dssoasNpTask.setMdftime(new Date());
//        dssoasNpTask.setTaskpri(12);
//        dssoasNpTask.setTaskdes("abv");
//        entityTemplate.save(dssoasNpTask);
//        dssoasNpTask = entityTemplate.get(DssoasNpTaskBindings.tasknameBinding().eq("abvsss"), DssoasNpTask.class);
//        System.out.println(dssoasNpTask);
//        System.out.println(simpleDateFormat.format(dssoasNpTask.getMdftime()));
//       List<DssoasNpTask> dssoasNpTaskList =  entityTemplate.getList(FinalConditions.FULL,DssoasNpTask.class,"order by mdftime desc",new Pagation(4,10));
//        dssoasNpTaskList.forEach(e-> System.out.println(e));

//        OracleTableDefinitionGetter oracleTableDefinitionGetter = new OracleTableDefinitionGetter(bascis);
//        //TableBean tableBean = oracleTableDefinitionGetter.table("DSSOAS_SE_TERESULT");
//        List<TableBean> tableBeanList = oracleTableDefinitionGetter.likeTable("DSSOAS_NP_FREQPOTHOLING");
//        for(TableBean tableBean :tableBeanList){
//            GeneraterOne generaterOne = new GeneraterOne(tableBean,"com.yu.kj","d:\\f");
//            generaterOne.start();
//        }

        //System.out.println(oracleTableDefinitionGetter.table("DSSOAS_SE_TERESULT"));

        //oracleTableDefinitionGetter.tables().forEach(e-> System.out.println(e));
//        String[] bi = {DssoasNpTaskBindings.tasknameBinding().getName(),DssoasNpTaskBindings.taskpriBinding().getName()};
//        AggregationParams aggregationParams0 = new AggregationParams();
//        aggregationParams0.setAlias("c");
//        aggregationParams0.setBindName("taskpri");
//        aggregationParams0.setAggregationSymbol(AggregationSymbol.COUNT);
//        AggregationParams aggregationParams1 = new AggregationParams();
//        aggregationParams1.setAlias("s");
//        aggregationParams1.setBindName("taskpri");
//        aggregationParams1.setAggregationSymbol(AggregationSymbol.SUM);
//        List<AggregationEntity> aggregationEntityList = entityTemplate.groupBy(FinalConditions.FULL, DssoasNpTask.class, bi, aggregationParams0,aggregationParams1);
//
//       for(AggregationEntity aggregationEntity:aggregationEntityList){
//           System.out.println(aggregationEntity.getDaoEntity());
//           System.out.println(aggregationEntity.getAggregationValues().get(0).getValue());
//           System.out.println(aggregationEntity.getAggregationValues().get(1).getValue());
//
//       }


// 连接查询

        //排序
//        Order order = new Order();
//        order.setColumFieldBinding(DssoasNpTaskBindings.taskdesBinding());
//        order.setSc(1);
//        Order order1 = new Order();
//        order1.setColumFieldBinding(DssoasNpTaskBindings.tasknameBinding());
//        order1.setSc(0);
//        //
//        EntityJoin entityJoin = BasicEntityJoin.newBasicEntityJoin(DssoasNpTask.class,
//                DssoasNpTaskBindings.createtimeBinding().le(new Date()).or(DssoasNpTaskBindings.taskdesBinding().eq("abv")), Arrays.asList(order, order1),
//                Arrays.asList(DssoasNpTaskBindings.tasknameBinding()));
//
//
//        EntityJoin entityJoin1 = BasicEntityJoin.newBasicEntityJoin(User.class,
//                UserBindings.idBinding().mt(1).or(UserBindings.idBinding().me(12)), null,
//                null);
//
//
//        System.out.println(entityJoin.sql(0));
//        System.out.println(entityJoin1.sql(0));
////        EntityJoinRelation joinRelation = new EntityJoinRelation();
////        joinRelation.setFirst(DssoasNpTaskBindings.taskidBinding());
////        joinRelation.setSecond(UserBindings.idBinding());
////        joinRelation.setOper(CompareSymbol.EQ);
//        EntityJoin entityJoin2 = entityJoin.leftJoin(entityJoin1, BasicConditions.eq(DssoasNpTaskBindings.taskidBinding(), UserBindings.idBinding()));
//        System.out.println(entityJoin2.sql(0));
//        List<EntityJoinResult> entityJoinResults = entityTemplate.entityJoin(entityJoin2);
//        System.out.println("entityJoinResults size " + entityJoinResults.size());
//        for (EntityJoinResult entityJoinResult : entityJoinResults) {
//            System.out.println("=============");
//            DssoasNpTask dssoasNpTask = (DssoasNpTask) entityJoinResult.getDaoEntities().get(0);
//            User user = (User) entityJoinResult.getDaoEntities().get(1);
//
//            System.out.println(dssoasNpTask);
//            System.out.println(user);
//        }
//        System.out.println("=======分页查询======");
//        List<EntityJoinResult> entityJoinResults0 = entityTemplate.entityJoin(entityJoin2, new Pagation(1, 10));
//
//        for (EntityJoinResult entityJoinResult : entityJoinResults0) {
//            System.out.println("=============");
//            DssoasNpTask dssoasNpTask = (DssoasNpTask) entityJoinResult.getDaoEntities().get(0);
//            User user = (User) entityJoinResult.getDaoEntities().get(1);
//            System.out.println(dssoasNpTask);
//            System.out.println(user);
//        }

        //条件测试
//        System.out.println("=======ppppppppppp======");
//        String s = DssoasNpTaskBindings.createtimeBinding().le(new Date())
//                .or(DssoasNpTaskBindings.taskdesBinding().eq("abv"))
//                .and(DssoasNpTaskBindings.createtimeBinding().le(new Date()))
//                .or(DssoasNpTaskBindings.taskdesBinding().isnotNull())
//                .and(DssoasNpTaskBindings.taskdesBinding().isNull())
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("ee"))
//                .or(DssoasNpTaskBindings.taskdesBinding().eq(DssoasNpTaskBindings.createtimeBinding()))
//                .or(new BasicConditions(DssoasNpTaskBindings.taskdesBinding().operate(DssoasNpTaskBindings.createtimeBinding(), OperationSymbol.ADD), DssoasNpTaskBindings.createtimeBinding(), CompareSymbol.LE))
//                .express();
//        CompositeExpress.EXPRESS_FORMAT_MODEL = CompositeExpress.EXPRESS_FORMAT_MODEL_STRICT;
//        s = DssoasNpTaskBindings.createtimeBinding().le(new Date())
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("abv"))
//                .and(DssoasNpTaskBindings.createtimeBinding().le(new Date()))
//                .and(DssoasNpTaskBindings.taskdesBinding().isnotNull())
//                .and(DssoasNpTaskBindings.taskdesBinding().isNull())
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("ee"))
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("ee"))
//                .or(DssoasNpTaskBindings.taskdesBinding().like("ee"))
//                .express();
//        System.out.println(s);
//        CompositeExpress.EXPRESS_FORMAT_MODEL = CompositeExpress.EXPRESS_FORMAT_MODEL_STANDARD;
//        s = DssoasNpTaskBindings.createtimeBinding().le(new Date())
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("abv"))
//                .and(DssoasNpTaskBindings.createtimeBinding().le(new Date()))
//                .and(DssoasNpTaskBindings.taskdesBinding().isnotNull())
//                .and(DssoasNpTaskBindings.taskdesBinding().isNull())
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("ee"))
//                .and(DssoasNpTaskBindings.taskdesBinding().eq("ee"))
//                .or(DssoasNpTaskBindings.taskdesBinding().like("ee"))
//                .express();
//        System.out.println(s);

        jdbcTemplete.getExcuter("insert into DSSOAS_RECORD_ACCESSNEW (SID,GWID,BEAMID,ACCESSNUM,ACCESSSUCCESSNUM,\n" +
                "STARTTIME,ENDTIME,REPORTTIME,ISDEALED,SATID) values (SEQ_DSSOAS_Record_ACCESS.Nextval,1,null,null,null,null,null,null,null,null)").excute();
    }
}