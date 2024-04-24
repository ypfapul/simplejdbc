package org.ypf.generic.orm.entityoper.critera;

import org.ypf.generic.orm.entityoper.DaoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接查询结果
 *
 * @date: 2022/6/30 9:43
 */
public class EntityJoinResult {
    List<DaoEntity> daoEntities = new ArrayList<>();

    public List<DaoEntity> getDaoEntities() {
        return daoEntities;
    }

    public void setDaoEntities(List<DaoEntity> daoEntities) {
        this.daoEntities = daoEntities;
    }
}
