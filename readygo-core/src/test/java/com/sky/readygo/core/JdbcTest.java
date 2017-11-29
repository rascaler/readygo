package com.sky.readygo.core;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by rascaler on 11/29/17.
 */
public class JdbcTest {

    // 动态创建jndi

    @Test
    public void testDruid() throws Exception{

        DruidDataSource dataSource = new DruidDataSource();
        // 设置druid日志
        final Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setConnectionLogEnabled(false);
        filter.setStatementLogEnabled(true);
        filter.setResultSetLogEnabled(true);
        filter.setStatementExecutableSqlLogEnable(true);
        dataSource.setProxyFilters(new ArrayList<Filter>(){{add(filter);}});
        //设置连接
        dataSource.setUrl(" jdbc:mysql://127.0.0.1:3306/rbac?allowMultiQueries=true&tinyInt1isBit=false&zeroDateTimeBehavior=convertToNull");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setValidationQuery("select 1");
        dataSource.init();

        // 查询
        DruidPooledConnection connection =  dataSource.getConnection();
        // 获取所有表
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, null, new String[] {"TABLE"});
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("TABLE_NAME"));
        }
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from tb_user");
        while (result.next()){
            System.out.println(result.getObject("name"));
        }
        return;
    }

    @Test
    public void testJdbc() {

    }

}
