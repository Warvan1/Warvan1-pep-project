package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public List<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<>();

        try{
            String sql = "select * from message";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                messageList.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return messageList;
    }

    public Message getMessageWithID(int message_id){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "select * from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
    
    public Message insertNewMessage(Message message){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pkrs = ps.getGeneratedKeys();
            if(pkrs.next()){
                return new Message(
                    pkrs.getInt("message_id"), 
                    message.getPosted_by(),
                    message.getMessage_text(),
                    message.getTime_posted_epoch()
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
