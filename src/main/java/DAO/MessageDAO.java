package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message creatMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.getMessage_text());
            pstmt.setLong(3, message.getTime_posted_epoch());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int message_id = rs.getInt(1);
                    message.setMessage_id(message_id);

                    return message;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt(1), rs.getInt(2),  rs.getString(3),  rs.getLong(4));
                allMessages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allMessages;
    }

    public Message getMessage(int message_id) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, message_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Message(rs.getInt(1), rs.getInt(2),  rs.getString(3),  rs.getLong(4));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessage(int message_id) {
        Message message = this.getMessage(message_id);
        if (message == null) {
            return null;
        }

        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, message_id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public Message updateMessage(int message_id, String message_text) {
        Message message = this.getMessage(message_id);
        if (message == null) {
            return null;
        }

        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, message_text);
            pstmt.setInt(2, message_id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                message.setMessage_text(message_text);
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesFromUser(int account_id) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, account_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt(1), rs.getInt(2),  rs.getString(3),  rs.getLong(4));
                allMessages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allMessages;
    }
}
