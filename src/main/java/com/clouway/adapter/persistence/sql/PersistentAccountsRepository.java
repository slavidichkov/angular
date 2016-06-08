package com.clouway.adapter.persistence.sql;

import com.clouway.core.AccountsRepository;
import com.clouway.core.InsufficientAvailability;
import com.clouway.core.User;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentAccountsRepository implements AccountsRepository{
    private final DataSource dataSource;

    @Inject
    public PersistentAccountsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Double getBalance(User user) {
        Connection connection = null;
        try {
            Double balance=new Double(0);
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("SELECT balance FROM accounts WHERE userEmail=?");
            statement.setString(1,user.email);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()) {
                balance=resultSet.getDouble("balance");
            }
            return balance;
        } catch (SQLException e) {
            throw new DatabaseException();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DatabaseException();
                }
            }
        }
    }

    public Double deposit(User user, Double amount) {
        Double balance=getBalance(user);
        Double newBalance=balance+amount;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("UPDATE accounts SET balance=? WHERE userEmail=?");
            statement.setDouble(1,newBalance);
            statement.setString(2,user.email);
            statement.execute();
            statement.close();
            return newBalance;
        } catch (SQLException e) {
            throw new DatabaseException();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DatabaseException();
                }
            }
        }
    }

    public Double withdraw(User user, Double amount) throws InsufficientAvailability {
        Double balance=getBalance(user);
        Connection connection = null;
        if (amount < balance){
            Double newBalance=balance-amount;
            try {
                connection = dataSource.getConnection();
                PreparedStatement statement=connection.prepareStatement("UPDATE accounts SET balance=? WHERE userEmail=?");
                statement.setDouble(1,newBalance);
                statement.setString(2,user.email);
                statement.execute();
                statement.close();
                return newBalance;
            } catch (SQLException e) {
                throw new DatabaseException();
            }finally {
                if (connection!=null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new DatabaseException();
                    }
                }
            }
        }else {
            throw new InsufficientAvailability("Can not withdraw "+amount+" because balance is " +balance+"");
        }
    }

    public void register(User user) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("INSERT INTO accounts(userEmail,balance) VALUES (?,?)");
            statement.setString(1,user.email);
            statement.setDouble(2,new Double(0));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseException();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DatabaseException();
                }
            }
        }
    }
}
