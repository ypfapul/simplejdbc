package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.DaoTemplete;
import org.ypf.generic.orm.entityoper.jdbcdemo.EntityTemplateImpl;
import org.ypf.generic.orm.entityoper.jdbcdemo.ObjectTempleteImpl;

/**
 * @date: 2022/6/22 10:14
 */
public class EntityTempleteUtils {
    public static DaoTemplete getDaoTemplete(EntityTemplete entityTemplete) {
        EntityTemplateImpl entityTemplate1 = (EntityTemplateImpl) entityTemplete;
        ObjectTempleteImpl objectTemplete = (ObjectTempleteImpl) entityTemplate1.getObjectTemplete();
        return objectTemplete.getJdbcTemplete();
    }

    public static ObjectTemplete getObjectTemplete(EntityTemplete entityTemplete) {
        EntityTemplateImpl entityTemplate1 = (EntityTemplateImpl) entityTemplete;
        return entityTemplate1.getObjectTemplete();
    }
}
