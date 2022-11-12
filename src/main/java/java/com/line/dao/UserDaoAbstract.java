package java.com.line.dao;


//POJO java DB 연동



import java.com.line.domain.User;
import java.sql.*;
import java.util.Map;

public abstract class UserDaoAbstract {
    public abstract Connection makeConnection() throws SQLException;

    public void add() {
        Map<String, String> env = System.getenv();
        try {
            //DB 접속 (ex. sql workbench실행)
            Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                    env.get("DB_USER"), env.get("DB_PASSWORD"));

            //Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO user(id, name, password)VALUES (?,?,?);");
            pstmt.setString(1, "6");
            pstmt.setString(2, "Mimi");
            pstmt.setString(3, "1q2w3e4r");

            //Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id)  {
        Map<String, String> env = System.getenv();
        Connection c;
        try {

            c = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));

            //Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM user WHERE id = ?");
            pstmt.setString(1, id);

            //Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

