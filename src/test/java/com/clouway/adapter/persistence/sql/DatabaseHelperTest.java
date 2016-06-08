package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.TestUser;
import com.clouway.adapter.persistence.sql.util.TestingDatasource;
import com.google.common.base.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseHelperTest {
  private DatabaseHelperImpl databaseHelper;

  @Before
  public void setUp() throws SQLException {
    DataSource dataSource = new TestingDatasource().get();
    dataSource.getConnection().createStatement().execute("CREATE TABLE helperTests(name VARCHAR(40),city VARCHAR(30))");
    databaseHelper = new DatabaseHelperImpl(dataSource);
  }

  @Test
  public void insertingObject() throws SQLException {
    databaseHelper.executeUpdate("insert into helperTests(name,city) values(?,?)","ivan","sliven" );
  }

  @Test
  public void executeQueryWithExistingObject() throws SQLException {
    databaseHelper.executeUpdate("insert into helperTests(name,city) values(?,?)","ivan","sliven" );

    Optional<TestUser> optUser = databaseHelper.fetchOne("select * from helperTests where name=?", new RowFetcher<TestUser>() {
      public TestUser build(ResultSet resultSet) throws SQLException {
        return new TestUser(resultSet.getString("name"),resultSet.getString("city"));
      }
    }, "ivan");
    assertThat(optUser.isPresent(), is(true));
  }

  @Test
  public void executeQueryWithNotExistingObject() throws SQLException {
    Optional<TestUser> optUser = databaseHelper.fetchOne("select * from helperTests where name=?", new RowFetcher<TestUser>() {
      public TestUser build(ResultSet resultSet) throws SQLException {
        return new TestUser(resultSet.getString("name"),resultSet.getString("city"));
      }
    }, "ivan");
    assertThat(optUser.isPresent(), is(false));
  }

  @After
  public void tearDown() throws SQLException {
    new TestingDatasource().get().getConnection().createStatement().execute("DROP TABLE helperTests");
  }
}