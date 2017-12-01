package com.sky.readygo.core;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.sky.readygo.core.cglib.BeanGeneratorClass;
import com.sky.readygo.core.domain.User;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by rascaler on 11/29/17.
 */
public class JdbcTest {

    // 动态创建jndi

    @Test
    public void testMysql() throws Exception{

        DruidDataSource dataSource = getMysqlDataSource();
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
    public void testOracle() throws Exception{

        DruidDataSource dataSource = getOracleDataSource();

        // 查询
        DruidPooledConnection connection =  dataSource.getConnection();
        // 获取所有表
        DatabaseMetaData metaData = connection.getMetaData();
        // 注意 必须大写，否则无法获取到数据
        ResultSet resultSet = metaData.getTables("ORADEV", "RDB_APPOINTMENT", null, new String[] {"TABLE"});
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("TABLE_NAME"));
        }
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from tb_register_user");
        while (result.next()){
            System.out.println(result.getObject("REUS_ID"));
        }
        return;
    }



    @Test
    public void testColumns() throws Exception{
        DruidDataSource dataSource = getMysqlDataSource();
        // 查询
        DruidPooledConnection connection =  dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from tb_user");
        while (result.next()){
            System.out.println(result.getObject("name"));
        }
        return;
    }

    @Test
    public void testTable() throws Exception{
        DruidDataSource dataSource = getMysqlDataSource();
        // 查询
        DruidPooledConnection connection =  dataSource.getConnection();
//        PreparedStatement statement =  connection.prepareStatement("select * from tb_user");

        // 获取所有表
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, null, new String[] {"TABLE"});
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("TABLE_NAME"));
        }
        ResultSet colRet = metaData.getColumns(null,"%", "tb_user","%");
        while(colRet.next()) {
            String columnName = colRet.getString("COLUMN_NAME");
            String columnType = colRet.getString("TYPE_NAME");
            int datasize = colRet.getInt("COLUMN_SIZE");
            int digits = colRet.getInt("DECIMAL_DIGITS");
            int nullable = colRet.getInt("NULLABLE");
            System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ nullable);
        }
//        Statement statement = connection.createStatement();
//        ResultSet result = statement.executeQuery();
//        ResultSetMetaData rsmd = result.getMetaData();
//        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
//            System.out.print("name=" + rsmd.getColumnName(i) + ";");
//            int jdbcType = rsmd.getColumnType(i);
//            System.out.print("type=" + jdbcType + ";");
//            System.out.print("typeName=" + JdbcType.forCode(jdbcType) + ";");
//            System.out.println("");
//        }
//        while (result.next()){
//            System.out.println(result.getObject("name"));
//        }
        return;
    }


    @Test
    public void testMybatis() throws Exception{
        // 生成mapper
        Class<?> mapper = generateMapper();
        // 生成model
        Class<?> model = generateModel();
        Class clazz = Class.forName("com.sky.readygo.core.domain.User");
        DruidDataSource dataSource = getMysqlDataSource();
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        List<Resource> resourceList = new ArrayList<Resource>();
        resourceList.add(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/UserMapper.xml"));
        bean.setMapperLocations(resourceList.toArray(new Resource[resourceList.size()]));
        return;
    }


    @Test
    public void testPrepareStatement() throws Exception{
        DruidDataSource dataSource = getMysqlDataSource();
        // 查询
        DruidPooledConnection connection =  dataSource.getConnection();
        PreparedStatement statement =  connection.prepareStatement("update tb_user set nickName=?,name=?,email=? where id=?");
        // 参数
        User user = new User();
        user.setId(12);
        user.setNickName("qing");
        user.setName("qing");
        user.setEmail("rascaler@163.com");

        final Configuration configuration = new Configuration();
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
        final Log log = LogFactory.getLog(BaseExecutor.class);
        PreparedStatement preparedStatement = PreparedStatementLogger.newInstance(statement, log,1);
        return;
    }







    public DruidDataSource getMysqlDataSource() throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        // 设置druid日志
        final Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setConnectionLogEnabled(false);
        filter.setStatementLogEnabled(true);
        filter.setResultSetLogEnabled(true);
        filter.setStatementExecutableSqlLogEnable(true);
        dataSource.setProxyFilters(new ArrayList<Filter>(){{add(filter);}});
        //设置连接
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/rbac?allowMultiQueries=true&tinyInt1isBit=false&zeroDateTimeBehavior=convertToNull&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setValidationQuery("select 1");
        dataSource.init();
        return dataSource;
    }

    public DruidDataSource getOracleDataSource() throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        // 设置druid日志
        final Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setConnectionLogEnabled(false);
        filter.setStatementLogEnabled(true);
        filter.setResultSetLogEnabled(true);
        filter.setStatementExecutableSqlLogEnable(true);
        dataSource.setProxyFilters(new ArrayList<Filter>(){{add(filter);}});
        //设置连接
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.200.16)(PORT = 1521))) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = ORADEV)))");
        dataSource.setUsername("rdb_appointment");
        dataSource.setPassword("123");
//        dataSource.setValidationQuery("select 1");
        dataSource.init();
        return dataSource;
    }




    public Class<?> generateMapper () {
       return BeanGeneratorClass.createBeanClass("com.sky.readygo.core.mapper.UserMapper",null);
    }

    public Class<?> generateModel () {
        final Map<String, Class<?>> properties =
                new HashMap<>();
        properties.put("id", Integer.class);
        properties.put("name", Integer.class);
        properties.put("createdDate", Date.class);
        properties.put("updatedDate", Date.class);
        properties.put("username", String.class);
        properties.put("password", String.class);
        properties.put("state", Integer.class);
        properties.put("nickName", String.class);
        properties.put("name", String.class);
        properties.put("email", String.class);
        properties.put("phone", String.class);
        properties.put("sex", Integer.class);
        properties.put("enterpriseId", Integer.class);
        return BeanGeneratorClass.createBeanClass("com.sky.readygo.core.domain.User",properties);
    }

    public String generateMapperXml() {
        String xml= "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                "<mapper namespace=\"com.sky.readygo.core.mapper.UserMapper\" >\n" +
                "  \n" +
                "</mapper>";
        return xml;
    }


}
