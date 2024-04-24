package org.ypf.generic.orm.entityoper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.ypf.generic.orm.entityoper.readmapperconf.BindBean;
import org.ypf.generic.orm.entityoper.readmapperconf.EntityBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2022/6/16 9:09
 */
public class DefaultEntityMapperSupplier implements BindMapperSupplier {

    List<BindBean> bindBeans = new ArrayList<>();
    List<EntityBean> entityBeans = new ArrayList<>();
    List<TableBean> tableBeans = new ArrayList<>();


    public DefaultEntityMapperSupplier() {
        init();
    }

    static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {


            reader = new BufferedReader(new InputStreamReader(inputStream));
            String readStr = null;
            while ((readStr = reader.readLine()) != null) {
                // 注释 滤过
                if (!readStr.trim().startsWith("//") && !StringUtils.isEmpty(readStr)) {
                    stringBuilder.append(readStr);
                }
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return stringBuilder.toString();


    }

    @Override
    public List<BindBean> bindBean() {
        return bindBeans;
    }

    @Override
    public List<EntityBean> entityBean() {
        return entityBeans;
    }

    @Override
    public List<TableBean> tableBean() {
        return tableBeans;
    }

    void init() {

        try {// 默认加载的配置

            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath*:**/*-mappingbinds.json");
            for (Resource resource : resources) {
                String conf = readInputStream(resource.getInputStream());
                JSONObject jsonObjectConf = JSON.parseObject(conf);
                bindBeans = JSON.parseArray(jsonObjectConf.getJSONArray("mapping").toJSONString(), BindBean.class);
                entityBeans = JSON.parseArray(jsonObjectConf.getJSONArray("entity").toJSONString(), EntityBean.class);
                tableBeans = JSON.parseArray(jsonObjectConf.getJSONArray("tables").toJSONString(), TableBean.class);
            }

        } catch (Exception e) {

        }


    }
}
