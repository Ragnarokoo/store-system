package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(11);
        address.setPhone("18596458523");
        address.setName("女朋友");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(11);
        System.out.println(count);

    }

    @Test
    public void findByUid() {
        List<Address> list = addressMapper.findByUid(11);
        System.out.println(list);
    }

    @Test
    public void findByAid() {
        System.err.println(addressMapper.findByAid(7));
    }

    @Test
    public void updateNotDefault() {
        addressMapper.updateNotDefault(11);
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(7,"小米",new Date());
    }

    @Test
    public void deleteByAid () {
        addressMapper.deleteByAid(4);
    }

    @Test
    public void findLastModified() {
        System.out.println(addressMapper.findLastModified(11));
    }
}

