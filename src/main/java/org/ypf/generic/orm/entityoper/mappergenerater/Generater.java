package org.ypf.generic.orm.entityoper.mappergenerater;

import com.alibaba.fastjson.JSON;
import org.ypf.generic.orm.entityoper.readmapperconf.BindBean;
import org.ypf.generic.orm.entityoper.readmapperconf.EntityBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;
import org.ypf.generic.dependency.AbstractProcessor;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2022/6/21 15:00
 */
public class Generater extends AbstractProcessor {
    TableDefinitionGetter tableDefinitionGetter;
    GenEntitySource genEntitySource;
    TableBean2EntityBean tableBean2EntityBean;
    String entityPackageNameSpace;
    String entityBindingPackageNameSpace;
    String dirStr;
    //是否支持jpa实体
    boolean supportJpa;


    public Generater(TableDefinitionGetter tableDefinitionGetter, GenEntitySource genEntitySource, TableBean2EntityBean tableBean2EntityBean, String entityPackageNameSpace, String entityBindingPackageNameSpace, String dirStr, boolean supportJpa) {
        this.tableDefinitionGetter = tableDefinitionGetter;
        this.genEntitySource = genEntitySource;
        this.tableBean2EntityBean = tableBean2EntityBean;
        this.entityPackageNameSpace = entityPackageNameSpace;
        this.entityBindingPackageNameSpace = entityBindingPackageNameSpace;
        this.dirStr = dirStr;
        this.supportJpa = supportJpa;
    }

    String entityPackageNameSpaceDir;
    String entityBindingPackageNameSpaceDir;
    Mappingbinds mappingbinds = new Mappingbinds();


    @Override
    protected void run() throws Exception {

        List<TableBean> tableBeanList = tableDefinitionGetter.allTable();
        for (TableBean tableBean : tableBeanList) {
            EntityBean entityBean = tableBean2EntityBean.map(tableBean, entityPackageNameSpace);
            genEntityFile(genEntitySource.genEntity(tableBean, entityPackageNameSpace, supportJpa), entityBean);
            genEntityBindingFile(genEntitySource.genEntityBinding(tableBean, entityBindingPackageNameSpace, entityPackageNameSpace), entityBean);
            BindBean bindBean = genEntityBindingFile(tableBean, entityBean);
            mappingbinds.getEntity().add(entityBean);
            mappingbinds.getMapping().add(bindBean);
            mappingbinds.getTables().add(tableBean);
        }
        genMappingbindsFile();
    }

    @Override
    protected void before() {
        mappingbinds.setEntity(new ArrayList<>());
        mappingbinds.setMapping(new ArrayList<>());
        mappingbinds.setTables(new ArrayList<>());
        genDirByEntityPackageNameSpace();
        genDirByEntityBindingPackageNameSpace();
    }

    void genDirByEntityPackageNameSpace() {
        entityPackageNameSpaceDir = dirStr;
        for (String pa : entityPackageNameSpace.split(".")) {
            entityPackageNameSpaceDir += "/" + pa;
            File fd = new File(entityPackageNameSpaceDir);
            if (!fd.exists()) {
                fd.mkdir();
            }
        }

    }

    void genDirByEntityBindingPackageNameSpace() {
        entityBindingPackageNameSpaceDir = dirStr;
        for (String pa : entityBindingPackageNameSpace.split(".")) {
            entityBindingPackageNameSpaceDir += "/" + pa;
            File fd = new File(entityBindingPackageNameSpaceDir);
            if (!fd.exists()) {
                fd.mkdir();
            }
        }

    }

    // 生成实体类源码文件
    void genEntityFile(String bytes, EntityBean entityBean) {
        Utils.genAndWrite2File(entityPackageNameSpaceDir + "/" + entityBean.getName() + ".java", bytes);
    }


    // 生成实体绑定类源码文件
    void genEntityBindingFile(String bytes, EntityBean entityBean) {
        Utils.genAndWrite2File(entityBindingPackageNameSpaceDir + "/" + entityBean.getName() + "Bindings.java", bytes);
    }

    // 生成json映射文件
    void genMappingbindsFile() {
        Utils.genAndWrite2File(dirStr + "/entiy-mappingbinds.json", JSON.toJSONString(mappingbinds));
    }

    BindBean genEntityBindingFile(TableBean tableBean, EntityBean entityBean) {
        BindBean bindBean = new BindBean();
        bindBean.setTable(tableBean.getName());
        bindBean.setJavaclass(entityBean.getName());
        bindBean.setName(entityBean.getName());
        return bindBean;
    }
}
