package com.autoTest.mapper;

import com.autoTest.pojo.Cases;

public interface CaseMapper {
    //开始加参数接口
    int addParam(Cases cases);
    //根据ID获取用例
    Cases getCaseById(int id);
    //插入依赖数据
    int insertDataById(Cases cases);
    //获取依赖参数
    String getDependDataById(int id);
    //根据依赖字段，更改原参数值
    int updateParam(Cases cases);
}
