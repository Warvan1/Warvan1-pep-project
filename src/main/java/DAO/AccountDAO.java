package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account getAccountWithUsername(String username){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "select * from account where username = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            
            ResultSet rs =  ps.executeQuery();

            while(rs.next()){
                return new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public Account getAccountWithAccountID(int account_id){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "select * from account where account_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, account_id);
            
            ResultSet rs =  ps.executeQuery();

            while(rs.next()){
                return new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public Account getAccountWithUsernameAndPassword(Account account){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            ResultSet rs =  ps.executeQuery();

            while(rs.next()){
                return new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public Account insertNewAccount(Account account){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "insert into account (username, password) values (?,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet pkrs = ps.getGeneratedKeys();
            if(pkrs.next()){
                return new Account(
                    pkrs.getInt("account_id"), 
                    account.getUsername(),
                    account.getPassword()
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
