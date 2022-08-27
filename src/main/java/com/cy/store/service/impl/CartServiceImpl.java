package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    /**
     * 后无车的业务层依赖于购物车的持久层以及商品的持久层
     */
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        // 如果为null ，表示这个商品从来没有添加到购物车中！
        if (result == null) {
            Cart cart = new Cart();
            // 补全数据: 参数传递的数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            // 补全价格：来自于商品中的数据
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            // 补全日志
            cart.setCreatedTime(date);
            cart.setCreatedUser(username);
            cart.setModifiedTime(date);
            cart.setModifiedUser(username);

            //执行输入的插入操作
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }
        } else { // 表示当前商品已经在购物车中，更新这条数据的num值
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(
                    result.getCid(),
                    num,
                    username,
                    date);
            if (rows != 1) {
                throw new UpdateException("更新数据时产生了未知的异常");
            }
        }
    }

    @Override
    public List<CartVo> findVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("商品数据不存在的异常");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问的异常");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid,num,username,new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据异常");
        }
        // 返回新的购物车数据的总量
        return num;
    }

    @Override
    public List<CartVo> getVoByCids(Integer uid, Integer[] cids) {
        List<CartVo> list = cartMapper.findVOByCids(cids);
        Iterator<CartVo> iterator = list.iterator();
        while (iterator.hasNext()) {
            CartVo cartVo = iterator.next();
            if (!cartVo.getUid().equals(uid)) { // 表示当前的数据不属于当前的用户
                // 从集合中移除这个元素
                list.remove(cartVo);
            }
        }
        return list;
    }
}
