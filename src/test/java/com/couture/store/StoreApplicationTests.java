package com.couture.store;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

    /**
     * 数据路连接池
     * 1.JDBC
     * 2.C3P0
     * 3.Hikari: 管理数据的连接对象
     * HikariProxyConnection@1829496747 wrapping com.mysql.cj.jdbc.ConnectionImpl@1d4fb213
     * @throws SQLException
     */
    @Test
    void getConnection() throws SQLException {

        System.out.println(dataSource.getConnection());
    }
}
