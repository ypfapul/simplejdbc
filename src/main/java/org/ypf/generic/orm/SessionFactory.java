package org.ypf.generic.orm;

import javax.sql.DataSource;

/**
 *
 */
public class SessionFactory {
    DataSource dataSource;

    public SessionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Session getSession() {
        return new JdbcSession(dataSource);
    }


}


