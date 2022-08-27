package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(11);
        cart.setNum(2);
        cart.setPrice(1000L);
        cart.setPid(10000005);
        cartMapper.insert(cart);
    }

    @Test
    public void updateByNumCid() {
        cartMapper.updateNumByCid(1,3,"张三",new Date());
    }

    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(11,10000005);
        System.err.println(cart);
    }

    @Test
    public void findVOByUid() {
        System.out.println(cartMapper.findVOByUid(11));
    }

    @Test
    public void findVOByCid() {
        System.out.println(cartMapper.findByCid(1));
    }

    @Test
    public void findVOByCids() {
        Integer[] cids = {1,2,3,8,44};
        System.out.println(cartMapper.findVOByCids(cids));
    }

}

