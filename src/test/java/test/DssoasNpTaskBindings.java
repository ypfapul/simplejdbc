package test;


import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.entityoper.EntityMapperRepository;

/**
 * 快速创建实体绑定对象类
 */

public class DssoasNpTaskBindings {
    public static Class target = DssoasNpTask.class;
    public static ColumFieldBinding taskidBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("taskid");
    }

    public static ColumFieldBinding tasknameBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("taskname");
    }

    public static ColumFieldBinding tasktypeBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("tasktype");
    }

    public static ColumFieldBinding taskpriBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("taskpri");
    }

    public static ColumFieldBinding taskstarttimeBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("taskstarttime");
    }

    public static ColumFieldBinding taskendtimeBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("taskendtime");
    }

    public static ColumFieldBinding createtimeBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("createtime");
    }

    public static ColumFieldBinding mdftimeBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("mdftime");
    }

    public static ColumFieldBinding deltimeBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("deltime");
    }

    public static ColumFieldBinding taskdesBinding() {
        return EntityMapperRepository.get(target).getColumFieldBinding("taskdes");
    }


}