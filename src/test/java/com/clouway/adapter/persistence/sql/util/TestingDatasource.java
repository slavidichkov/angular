package com.clouway.adapter.persistence.sql.util;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class TestingDatasource {
  public DataSource get(){
    MysqlConnectionPoolDataSource dataSource;
    dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/banktests");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    return dataSource;
  }
}
