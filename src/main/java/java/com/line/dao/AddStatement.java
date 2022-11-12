package java.com.line.dao;

import com.line.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{
    User user;

    public AddStatement(User user){
        this.user = user;
    }

    StatementStrategy st = new StatementStrategy() {
        public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO user(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            return pstmt;
        }
    };

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        // Query문 작성
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO user(id, name, password) VALUES(?,?,?);");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());
        return pstmt;
    }

}
