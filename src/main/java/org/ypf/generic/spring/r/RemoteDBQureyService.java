package org.ypf.generic.spring.r;

import com.alibaba.fastjson.JSONObject;
import org.ypf.generic.orm.ResultSetProxy;


import java.util.List;
import java.util.Map;

/**
 * 远程数据库查询接口
 */
public interface RemoteDBQureyService extends RemoteDBService {

    List<Map<String, Object>> qurey(String sql);

    List<Map<String, Object>> qurey(RemoteSqlProxy sql);

    List<JSONObject> qureyJSONObject(RemoteSqlProxy sql);

    ResultSetProxy qureyResultSetProxy(RemoteSqlProxy sql);
}
