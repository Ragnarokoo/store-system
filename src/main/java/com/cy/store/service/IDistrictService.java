package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistrictService {

    /**
     * 根据父代号来查询区域信息(省、市、区)
     * @param parent 父代号
     * @return 多个区域的信息
     */
    List<District> getByParent(String parent);

    /**
     * 根据code查询省市区
     * @param code 省市区对应的code
     * @return 查询到的数据
     */
    String getNameByCode(String code);

}
