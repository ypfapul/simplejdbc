package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.Session;
import org.ypf.generic.orm.SessionFactory;
import org.ypf.generic.orm.entityoper.readmapperconf.ColumnBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @date: 2022/6/21 16:11
 */
public class OracleTableDefinitionGetter implements TableDefinitionGetter {
    DataSource dataSource;

    Session session;

    public OracleTableDefinitionGetter(DataSource dataSource) {
        this.dataSource = dataSource;
        SessionFactory sessionFactory = new SessionFactory(dataSource);
        session = sessionFactory.getSession();
    }

    @Override
    public List<TableBean> allTable() {
        List<TableBean> list = new ArrayList<>();

        List<Map<String, Object>> listTable = session.listQurey("SELECT TABLE_NAME FROM user_tables");
        for (Map<String, Object> tableMap : listTable) {
            String tableName = tableMap.get("TABLE_NAME").toString();
            list.add(table(tableName));

        }


        return list;
    }

    @Override
    public TableBean table(String table) {
        TableBean tableBean = null;
        List<Map<String, Object>> listTable = session.listQurey("SELECT TABLE_NAME FROM user_tables where TABLE_NAME='" + table + "'");
        for (Map<String, Object> tableMap : listTable) {
            String tableName = tableMap.get("TABLE_NAME").toString();
            System.out.println(tableName);
            tableBean = new TableBean();
            tableBean.setColumns(new ArrayList<>());
            tableBean.setName(tableName);
            List<Map<String, Object>> listColumns = session.listQurey("SELECT * FROM user_tab_columns WHERE table_name='" + tableName + "'");
            List<Map<String, Object>> primary = session.listQurey("SELECT COLUMN_NAME FROM user_cons_columns a,user_constraints b WHERE a.constraint_name = b.constraint_name AND b.constraint_type = 'P' AND a.table_name='" + tableName + "'");
            for (Map<String, Object> columnMap : listColumns) {
                String columnName = columnMap.get("COLUMN_NAME").toString();
                if (columnName.contains("....."))
                    continue;
                String columnDataType = columnMap.get("DATA_TYPE").toString();
                Integer dataPrecision = columnMap.get("DATA_PRECISION") == null ? null : Integer.parseInt(columnMap.get("DATA_PRECISION").toString());
                Integer dataScale = columnMap.get("DATA_SCALE") == null ? null : Integer.parseInt(columnMap.get("DATA_SCALE").toString());
                ColumnBean columnBean = new ColumnBean();
                tableBean.getColumns().add(columnBean);
                columnBean.setName(columnName);
                if (primary.size() > 0 && primary.get(0).get("COLUMN_NAME").toString().equals(columnName)) {
                    columnBean.setPrimary(1);
                    columnBean.setPrimaryStrategy(0);
                } else {
                    columnBean.setPrimary(0);
                }
                if (columnDataType.equals("NUMERIC")) {
                    if (dataScale < 1) {//整数
                        if (dataPrecision < 10) {
                            columnBean.setType(0);// int
                        } else {
                            columnBean.setType(1); //long
                        }

                    } else {
                        columnBean.setType(2);
                    }

                } else if (columnDataType.equals("VARCHAR")) {
                    columnBean.setType(4);

                } else if (columnDataType.equals("DATE")) {
                    columnBean.setType(3);

                } else if (columnDataType.equals("TIMESTAMP")) {
                    columnBean.setType(3);
                }

            }

        }


        return tableBean;
    }

    @Override
    public List<TableBean> likeTable(String like) {
        List<TableBean> list = new ArrayList<>();

        List<Map<String, Object>> listTable = session.listQurey("SELECT TABLE_NAME FROM user_tables where TABLE_NAME like '%" + like + "%'");
        for (Map<String, Object> tableMap : listTable) {
            String tableName = tableMap.get("TABLE_NAME").toString();
            list.add(table(tableName));

        }


        return list;
    }

    @Override
    public List<TableBean> regular(String regular) {
        return null;
    }
}
