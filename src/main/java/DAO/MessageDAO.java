package DAO;

import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;

public class MessageDAO {

    public Message createMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if(rs.next())
            {
                int generated_message_id = (int) rs.getLong(1);
                Message newMessage = getMessageByMessageID(generated_message_id);
                return newMessage;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                Message message = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                messages.add(message);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return messages;
    }

    public Message getMessageByMessageID(int messageID)
    {
        Connection connection = ConnectionUtil.getConnection();
    
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
    
        }catch(SQLException e){
            System.out.println(e.getMessage());
    
        }
        return null;
    }

    public Message deleteMessageByMessageID(int messageID)
    {
        Message messageToBeDeleted = getMessageByMessageID(messageID);
        Connection connection = ConnectionUtil.getConnection();

        
        try {
            if(messageToBeDeleted != null)
            {
                String sql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, messageID);
                preparedStatement.executeUpdate(); 
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return messageToBeDeleted;
    }

    public Message updateMessageByMessageID(int messageID, Message messageUpdate)
    {
        Message messageToBeUpdated = getMessageByMessageID(messageID);
        Connection connection = ConnectionUtil.getConnection();

        if(messageUpdate.getMessage_text().isBlank() || messageUpdate.getMessage_text().length() > 250)
        {
            return null;
        }
        
        if(messageToBeUpdated == null)
        {
            return null;
        }

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageUpdate.getMessage_text());
            preparedStatement.setInt(2, messageID);
            preparedStatement.executeUpdate();

            return getMessageByMessageID(messageID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getMessagesByUserID(int userID)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                Message message = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                messages.add(message);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return messages;
    }
}
