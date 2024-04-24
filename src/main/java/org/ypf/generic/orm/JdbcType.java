package org.ypf.generic.orm;


import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * jdbc 类型处理枚举
 *
 * @date: 2022/6/1 16:21
 */
public enum JdbcType implements DataTypeDefinition {


    OBJECT(-1) {
        @Override
        public void setValue(Object value, int index, PreparedStatement rs) throws SQLException {
            rs.setObject(index, value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws Exception {
            return rs.getObject(index);
        }

        @Override
        public List<Class> classesMapper() {
            return null;
        }


    },
    BYTE(5) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setByte(index, (Byte) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getByte(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Byte.class, byte.class);
        }
    },
    BYTES(51) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            byte[] bytes = null;
            if (value instanceof Byte[]) {
                Byte[] bytes1 = (Byte[]) value;
                bytes = new byte[bytes1.length];
                for (int i = 0; i < bytes1.length; i++) {
                    bytes[i] = bytes1[i];
                }
            } else {
                bytes = (byte[]) value;
            }

            st.setBytes(index, bytes);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getBytes(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Byte[].class, byte[].class);
        }
    },
    SHORT(6) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setShort(index, (Short) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getShort(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(short.class, Short.class);
        }
    },
    CHAR(7) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            Character character = (Character) value;

            st.setString(index, character.toString());
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getString(index).charAt(0);

        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(char.class, Character.class);
        }
    },
    INT(0) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setInt(index, (Integer) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            if (rs.getObject(index) == null)
                return null;
            ;
            return rs.getInt(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Integer.class, int.class);
        }
    },
    LONG(1) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setLong(index, (Long) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            if (rs.getObject(index) == null)
                return null;
            return rs.getLong(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Long.class, long.class);
        }
    },
    STRING(4) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setString(index, (String) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getString(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(String.class);
        }
    },
    FLOAT(8) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setFloat(index, (Float) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            if (rs.getObject(index) == null)
                return null;
            return rs.getFloat(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Float.class, float.class);
        }
    },
    DOUBLE(2) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setDouble(index, (Double) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            if (rs.getObject(index) == null)
                return null;
            return rs.getDouble(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Double.class, double.class);
        }
    },
    DATE(3) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            Date date = (Date) value;
            Timestamp timestamp = new Timestamp(date.getTime());
            st.setTimestamp(index, timestamp);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getTimestamp(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(Date.class, Timestamp.class);
        }
    },
    BIGDECIMAL(9) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
            st.setBigDecimal(index, (BigDecimal) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getBigDecimal(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(BigDecimal.class);
        }
    },
    BOOLEAN(10) {
        @Override
        public void setValue(Object value, int index, PreparedStatement st) throws SQLException {

            st.setBoolean(index, (Boolean) value);
        }

        @Override
        public Object getValue(int index, ResultSet rs) throws SQLException {
            return rs.getBoolean(index);
        }

        @Override
        public List<Class> classesMapper() {
            return Arrays.asList(boolean.class, Boolean.class);
        }
    };

    int code;

    JdbcType(int code) {
        this.code = code;
    }

    //优先类
    public static Class jdbcTypeToPriorClass(JdbcType jdbcType) {

        if (jdbcType != OBJECT) {
            List<Class> list = jdbcType.classesMapper();
            for (Class c : list) {
                String name = c.getSimpleName();
                if (!name.equals(name.toLowerCase())) {// 如果类为引用类型者
                    return c;
                }

            }
            return list.get(0);
        }

        return Object.class;

    }


    public static JdbcType classToJdbcType(Class<?> type) {

        JdbcType[] jdbcTypes = JdbcType.values();
        for (JdbcType jdbcType1 : jdbcTypes) {
            if (jdbcType1 != OBJECT && jdbcType1.classesMapper().contains(type)) return jdbcType1;
        }

        return OBJECT;

    }

    public static JdbcType codeTojdbcType(int jdbcTypeCode) {
        JdbcType[] jdbcTypes = JdbcType.values();
        for (JdbcType jdbcType1 : jdbcTypes) {
            if (jdbcType1 != OBJECT && jdbcType1.code() == jdbcTypeCode) return jdbcType1;
        }
        return OBJECT;

    }

    public void set(Object value, int index, PreparedStatement st) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.NULL);
        } else {
            setValue(value, index, st);
        }
    }

    @Override
    public int code() {
        return code;
    }


    public void setValue(Object value, int index, PreparedStatement st) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.NULL);
        }
    }
}
