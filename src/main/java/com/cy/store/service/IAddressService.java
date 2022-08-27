package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

/**
 * 收货地址业务层接口
 */
public interface IAddressService {

    /**
     * 用户收货地址的新增
     *
     * @param uid      用户id
     * @param username 用户名
     * @param address  用户收货地址数据
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 根据用户id查询收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> getByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址数据为默认收货地址
     *
     * @param aid      收货地址的id
     * @param uid      用户的id
     * @param username 修改人
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 用户名
     */
    void delete(Integer aid,Integer uid, String username);

    /**
     * 根据aid获取收货地址数据
     * @param uid 用户id
     * @param aid 收货地址id
     * @return 收货地址数据
     */
    Address getByAid(Integer aid,Integer uid);
}
