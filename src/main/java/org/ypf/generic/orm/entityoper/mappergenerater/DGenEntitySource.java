package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.JdbcType;
import org.ypf.generic.orm.entityoper.readmapperconf.ColumnBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;

import java.util.List;

/**
 * @date: 2022/6/21 17:10
 */
public class DGenEntitySource implements GenEntitySource {
    @Override
    public String genEntity(TableBean tableBean, String packageNameSpace, boolean supportJpa) {
        StringBuilder sb = new StringBuilder();
        sb.append("package");
        sb.append(" ");
        sb.append(packageNameSpace);
        sb.append(";");
        sb.append("\n");
        sb.append("import org.extra.pros.dbu.api.entityoper.DaoEntity;");
        sb.append("\n");
        if (supportJpa) {
            sb.append("import javax.persistence.*;");
            sb.append("\n");
        }

        if (tableBean.getColumns().stream().filter(e -> e.getType() == 3).findFirst().isPresent()) {
            sb.append("import java.util.Date;");
            sb.append("\n");
        }
        sb.append("\n");
        if (supportJpa) {
            sb.append("@Entity");
            sb.append("\n");
            sb.append("@Table(name = \"" + tableBean.getName() + "\")");
            sb.append("\n");
        }

        sb.append("public class ");
        sb.append(Utils.tableName2EntityName(tableBean.getName()));
        sb.append(" ");
        sb.append("implements DaoEntity ");
        sb.append("{");
        sb.append("\n");
        List<ColumnBean> columnBeanList = tableBean.getColumns();
        // 生成字段
        for (ColumnBean columnBean : columnBeanList) {
            if (supportJpa) {
                if (columnBean.getPrimary() == 1) {
                    sb.append("@Id");
                    sb.append("\n");
                }
                if (columnBean.getPrimaryStrategy() == 1) {
                    sb.append("@GeneratedValue");
                    sb.append("\n");
                }
                sb.append("@Column(name = \"" + columnBean.getName() + "\")");
                sb.append("\n");

            }
            sb.append("private ");
            JdbcType jdbcType = JdbcType.codeTojdbcType(columnBean.getType());
            Class c = JdbcType.jdbcTypeToPriorClass(jdbcType);
            sb.append(c.getSimpleName());
            sb.append(" ");
            sb.append(Utils.columnName2FiledName(columnBean.getName()));
            sb.append(";");
            sb.append("\n");


        }
        // 生成get set 方法
        for (ColumnBean columnBean : columnBeanList) {
            JdbcType jdbcType = JdbcType.codeTojdbcType(columnBean.getType());
            Class c = JdbcType.jdbcTypeToPriorClass(jdbcType);
            String type = c.getSimpleName();
            sb.append(Utils.genGetMethod(Utils.columnName2FiledName(columnBean.getName()), type));
            sb.append("\n");
            sb.append(Utils.genSetMethod(Utils.columnName2FiledName(columnBean.getName()), type));
            sb.append("\n");

        }
        sb.append("}");
        // System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public String genEntityBinding(TableBean tableBean, String packageNameSpace, String entityPackageNameSpace) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ");
        sb.append(packageNameSpace);
        sb.append(";");
        sb.append("\n");
        sb.append("import org.extra.pros.dbu.api.entityoper.ColumFieldBinding;");
        sb.append("\n");
        sb.append("import org.extra.pros.dbu.api.entityoper.EntityMapperRepository;");
        sb.append("\n");
        if (!packageNameSpace.equals(entityPackageNameSpace)) {
            sb.append("import ");
            sb.append(entityPackageNameSpace);
            sb.append(".");
            sb.append(Utils.tableName2EntityName(tableBean.getName()));
            sb.append(";\n");
        }
        sb.append("\n");
        sb.append("public class ");
        sb.append(Utils.tableName2EntityName(tableBean.getName()) + "Bindings");
        sb.append(" ");
        sb.append("{");
        sb.append("\n");
        sb.append("public static Class target =");
        sb.append(Utils.tableName2EntityName(tableBean.getName()));
        sb.append(".class;");
        sb.append("\n");
        List<ColumnBean> columnBeanList = tableBean.getColumns();
        // 生成方法
        for (ColumnBean columnBean : columnBeanList) {

            sb.append(" public static ColumFieldBinding ");
            sb.append(Utils.columnName2FiledName(columnBean.getName()));
            sb.append("Binding() {");
            sb.append("\n");
            sb.append(" return EntityMapperRepository.get(target).getColumFieldBinding(");
            sb.append("\"");
            sb.append(Utils.columnName2FiledName(columnBean.getName()));
            sb.append("\"");
            sb.append(")");
            sb.append(";");
            sb.append("}");
            sb.append("\n");

        }

        sb.append("}");

        return sb.toString();
    }
}
