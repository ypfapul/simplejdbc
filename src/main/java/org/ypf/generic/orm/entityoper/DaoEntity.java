package org.ypf.generic.orm.entityoper;

/**
 * @date: 2022/5/31 8:56
 */
public interface DaoEntity {
    default EntityMapper entityMapper() {
        return EntityMapperRepository.get(this.getClass());
    }
}
