package DAO;

import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.Account;

public class AccountDAO {
    
    public Account registerUser(Account account)
    {
        if(getAccountByUsername(account.getUsername()) != null)
        {
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();

        
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            Account registeredUser = getAccountByUsername(account.getUsername());
            return registeredUser;
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Account loginUser(Account account)
    {
        
        return getAccountByUsername(account.getUsername());
        
    }

    public Account getAccountByUsername(String username)
    {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        return null;
    }
    
    public Account getAccountByID(int accountID)
    {
        Connection connection = ConnectionUtil.getConnection();
    
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setInt(1, accountID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
    
        }catch(SQLException e){
            System.out.println(e.getMessage());
    
        }
        return null;
    }
}
