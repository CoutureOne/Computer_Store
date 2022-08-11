package com.couture.store;

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
@SpringBootApplication
@MapperScan("com.couture.store.mapper")
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    public MultipartConfigElement getMultipartFileElement() {
        // 创建一个配置的工厂类
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // 设置需要创建对象的相关信息
        factory.setMaxFileSize(DataSize.of
                (10, DataUnit.MEGABYTES));

        factory.setMaxFileSize(DataSize.of
                (15, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
