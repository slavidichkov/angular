package com.clouway.adapter.persistence.sql;

import com.clouway.core.AccountsRepository;
import com.clouway.core.LoggedUsersRepository;
import com.clouway.core.SessionLength;
import com.clouway.core.SessionsRepository;
import com.clouway.core.Time;
import com.clouway.core.UsersRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistenceModule extends AbstractModule {
  @Override
  protected void configure() {

  }

  @Provides
  public DatabaseHelper getDatabaseHelper(DataSource dataSource){
    return new DatabaseHelperImpl(dataSource);
  }

  @Provides
  public AccountsRepository getAccountsRepository(DataSource dataSource){
    return new PersistentAccountsRepository(dataSource);
  }

  @Provides
  public UsersRepository getUsersRepository(DatabaseHelper databaseHelper){
    return new PersistentUsersRepository(databaseHelper);
  }

  @Provides
  public SessionsRepository getSessionsRepository(DatabaseHelper databaseHelper, @SessionLength Long sessionLength, LoggedUsersRepository loggedUsersRepository, Time time){
    return new PersistentSessionsRepository(databaseHelper,sessionLength,loggedUsersRepository, time);
  }

  @Provides
  public LoggedUsersRepository getLoggedUsersRepository(DataSource dataSource){
    return new PersistentLoggedUsersRepository(dataSource);
  }

  @Provides
  public DataSource getDataSource(){
    MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/bank");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    return dataSource;
  }
}
