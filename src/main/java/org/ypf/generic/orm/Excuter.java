package org.ypf.generic.orm;

import org.ypf.generic.orm.entityoper.SqlKeywords;
import org.ypf.generic.orm.exception.JdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @date: 2021/6/22 19:27
 */
public class Excuter<T> extends BaseExcuter<Integer, T> {
    public static final String RETURN_GENERATED_KEYS_COLUMN_NAME = "$RETURN_GENERATED_KEYS_COLUMN_NAME";
    public static final String RETURN_GENERATED_KEYS_COLUMN_JDBCTYPE = "$RETURN_GENERATED_KEYS_COLUMN_JDBCTYPE";
    private static Logger logger = LoggerFactory.getLogger(Excuter.class);
    private List ids;
    /**
     * 1 返回主键
     */
    private int model;

    public Excuter(Supplier<Connection> connectionSupplier, String sql, ConsumerPreparedStatement<T> consumerPreparedStatement, T condition) {
        super(connectionSupplier, sql, consumerPreparedStatement, condition);
    }

    //获取id
    private void idReturn(JdbcType jdbcType) throws Exception {
        if (model == PreparedStatement.RETURN_GENERATED_KEYS && sql.toLowerCase().contains(SqlKeywords.INSERT.value)) {
            ResultSet idrt = preparedStatement.getGeneratedKeys();
            ids = new ArrayList();
            while (idrt.next()) {
                ids.add(jdbcType.getValue(1, idrt));
            }
            idrt.close();
        }
    }

    @Override
    protected Integer result() throws Exception {
        int r = preparedStatement.executeUpdate();
        logger.debug("excute result {}", r);
        return r;
    }

    @Override
    protected PreparedStatement genPreparedStatement() throws SQLException {
        if (model == PreparedStatement.RETURN_GENERATED_KEYS && sql.toLowerCase().contains(SqlKeywords.INSERT.value)) {
            JdbcType jdbcType = (JdbcType) jdbcExcuterContext.get(RETURN_GENERATED_KEYS_COLUMN_JDBCTYPE);
            addWhenAfter(h -> {// 添加任务
                try {
                    idReturn(jdbcType);
                } catch (Exception e) {
                    throw new JdbcException(e);
                }
            });

            if (dbType == 0) {// oralce
                String[] idc = {jdbcExcuterContext.get(RETURN_GENERATED_KEYS_COLUMN_NAME).toString()};
                return connectionSupplier.get().prepareStatement(sql, idc);
            } else {
                return connectionSupplier.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            }

        }
        return super.genPreparedStatement();
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public List<?> getIds() {
        return ids;
    }
}
