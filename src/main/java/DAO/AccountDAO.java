package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    
    public Account register(Account account) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    int account_id = rs.getInt(1);
                    account.setAccount_id(account_id);
                    return account;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account login(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int account_id = rs.getInt(1);
                account.setAccount_id(account_id);

                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
