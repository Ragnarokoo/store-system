package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据名称查询对应的code
     * @param code 省市区各自对应的code
     * @return 查询到的省市区对应code结果
     */
    String findNameByCode(String code);
}
