package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@Configuration
@MapperScan("com.cy.store.mapper") // 扫描所有的mapper文件
@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    /**
     * 配置文件上传的另一种配置：代替了application.properties中的配置!
     * @return
     */
    @Bean
    public MultipartConfigElement getMultipartConfigElement() {
        // 创建一个配置的工厂类对象
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // 设置需要创建的相关信息
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(15, DataUnit.MEGABYTES));

        // 通过工厂类来创建MultipartConfigElement对象！
        return factory.createMultipartConfig();
    }

}
