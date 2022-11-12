package java.com.line.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LocalConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        // DB접속 (ex sql workbeanch실행)
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/likelion-db",
                "root",
                "12345678");
        return c;
    }
}
