package com.autoTest.getdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoTest.mapper.CaseMapper;
import com.autoTest.pojo.Cases;
import com.autoTest.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private static Cases cases = null;
    private static Map<String, String> map = null;
    private static Map<String, String> param = null;
    private static Map<String, String> dependData = null;
    /**
     * 根据ID获取
     * */
    public static Cases getCasesAll(int id){
        return getMapper().getCaseById(id);
    }
    /**
     * 获取接口mapper
     */
    public static CaseMapper getMapper() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        return sqlSession.getMapper(CaseMapper.class);
    }

    /**
     * getUrl
     */
    public static String getUrl(int id) {
        String url = getMapper().getCaseById(id).getUrl();
        String uri = getMapper().getCaseById(id).getUri();
        return url + uri;
    }

    /**
     * getParam
     */
    public static Map<String, String> getParam(int id) {
        return JSONObject.parseObject(getMapper().getCaseById(id).getParam(), Map.class);
    }

    /**
     * getMethod
     */
    public static String getMethod(int id) {
        return getMapper().getCaseById(id).getMethod();
    }

    /**
     * 初始化插入参数，map转String
     * JSON.toJSONString(map);
     */
    public static void addParam(int id, Map<String, String> map) {
        cases = new Cases();
        String json = JSON.toJSONString(map);
        cases.setParam(json);
        cases.setId(id);
        getMapper().addParam(cases);
    }

    /**
     * 本接口被依赖字段，插入数据库
     */
    public static void insertDependData(int id, String key, Object value) {
        cases = new Cases();
        map = new HashMap<String, String>();
        map.put(key, String.valueOf(value));
        cases.setDependData(JSON.toJSONString(map));
        cases.setId(id);
        getMapper().insertDataById(cases);
    }

    /**
     * 根据被依赖case的ID，获取依赖参数map
     */
    private static Map<String, String> getDependDataParam(int id) {
        return JSONObject.parseObject(getMapper().getDependDataById(id), Map.class);
    }

    /**
     * 更新需要依赖的参数
     * @param id 当前case的id
     * @param idDependData 依赖字段的来源id
     */
    public static int updateParam(int id, int idDependData) {
        dependData = getDependDataParam(idDependData);
        param = getParam(id);
        for (String key : dependData.keySet()) {
            param.put(key, dependData.get(key));
        }
        cases=new Cases();
        cases.setId(id);
        cases.setParam(JSON.toJSONString(param));
        return getMapper().updateParam(cases);
    }
}
