package org.ypf.generic.dependency;

import org.ypf.generic.orm.JdbcTemplete;
import org.ypf.generic.orm.SessionFactory;
import org.ypf.generic.orm.entityoper.jdbcdemo.EntityTemplateImpl;
import org.ypf.generic.orm.entityoper.jdbcdemo.ObjectTempleteImpl;

import javax.sql.DataSource;


/**
 * @date: 2022/7/9 16:15
 */
public abstract class FastJDBCTest implements Test {

    protected JdbcTemplete jdbcTemplete;
    protected ObjectTempleteImpl objectTemplete;
    protected EntityTemplateImpl entityTemplate;
    protected SessionFactory sessionFactory;

    public JdbcTemplete getJdbcTemplete() {
        return jdbcTemplete;
    }

    public ObjectTempleteImpl getObjectTemplete() {
        return objectTemplete;
    }

    public EntityTemplateImpl getEntityTemplate() {
        return entityTemplate;
    }

    public FastJDBCTest(DataSource dataSource) {
        sessionFactory = new SessionFactory(dataSource);
        jdbcTemplete = new JdbcTemplete(sessionFactory);
        objectTemplete = new ObjectTempleteImpl(jdbcTemplete);
        entityTemplate = new EntityTemplateImpl(objectTemplete);
    }


}
