package org.ypf.generic.spring.r;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.ypf.generic.orm.DaoTemplete;
import org.ypf.generic.orm.ResultSetProxy;
import org.ypf.generic.orm.SqlProxy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 默认实现
 */
public abstract class AbsRemoteDBQureyService implements RemoteDBQureyService {
    DaoTemplete daoTemplete;

    public AbsRemoteDBQureyService(DaoTemplete daoTemplete) {
        this.daoTemplete = daoTemplete;
    }

    @Override
    public List<Map<String, Object>> qurey(String sql) {
        return daoTemplete.listQurey(sql);
    }

    @Override
    public List<Map<String, Object>> qurey(RemoteSqlProxy remoteSqlProxy) {
        SqlProxy sqlProxy = new SqlProxy();
        sqlProxy.setSql(remoteSqlProxy.getSql());
        for (Map.Entry<Integer, SqlValue> e : remoteSqlProxy.getIndexData().entrySet()) {
            SqlValue sqlValue = e.getValue();
            sqlValue.reload();
            sqlProxy.getIndexData().put(e.getKey(), sqlValue.getValue());
        }
        return daoTemplete.listQurey(sqlProxy);
    }

    @Override
    public List<JSONObject> qureyJSONObject(RemoteSqlProxy remoteSqlProxy) {
        return qurey(remoteSqlProxy).stream().map(e -> JSON.parseObject(JSON.toJSONString(e))).collect(Collectors.toList());
    }

    @Override
    public ResultSetProxy qureyResultSetProxy(RemoteSqlProxy remoteSqlProxy) {
        SqlProxy sqlProxy = new SqlProxy();
        sqlProxy.setSql(remoteSqlProxy.getSql());
        for (Map.Entry<Integer, SqlValue> e : remoteSqlProxy.getIndexData().entrySet()) {
            SqlValue sqlValue = e.getValue();
            sqlValue.reload();
            sqlProxy.getIndexData().put(e.getKey(), sqlValue.getValue());
        }
        return daoTemplete.listQureyResultSetProxy(sqlProxy);

    }


}
