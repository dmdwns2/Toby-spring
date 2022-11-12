package java.com.line.dao;

import com.line.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;


/*    //    private final DataSource dataSource;
//    private final JdbcContext jdbcContext; jdbcTemplate로 대체
//    private ConnectionMaker connectionMaker;*/


    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
            return user;
        }
    };


/*
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

//    public UserDao() {
//        this.connectionMaker = new AwsConnectionMaker();
//    }*/

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
//        this.dataSource = dataSource;
//        this.jdbcContext = new JdbcContext(dataSource);
    }

    public UserDao() {
//        this.dataSource = null;
//        this.jdbcContext = null;
    }




/* jdbcContect 분리로 삭제
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
*/

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update("INSERT INTO user(id, name, password) VALUES(?,?,?);",
                user.getId(), user.getName(), user.getPassword());
    }

/*        jdbcContext.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        // Query문 작성
                        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO user(id, name, password) VALUES(?,?,?);");
                        pstmt.setString(1, user.getId());
                        pstmt.setString(2, user.getName());
                        pstmt.setString(3, user.getPassword());
                        return pstmt;
                    }

                }
        );
        }*/

/*StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatementStrategy(st);*/

/*    public void add(User user) {
        try {
            // DB접속 (ex sql workbeanch실행)
            Connection c = connectionMaker.makeConnection(); // Query문 작성 PreparedStatement pstmt = c.prepareStatement("INSERT INTO user(id, name, password) VALUES(?,?,?);"); pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            //Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public User findById(String id) {
        String sql = "select * from user where id =?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
/*  RowMapper가 getCount 에서도 사용되기 때문에 위에서 통합하여 사용했기 때문에 제거
    RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
                return user;
            }
        };*/

/*        try {

            Connection c = dataSource.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM user WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }
            rs.close();
            pstmt.close();
            c.close();

            if (user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from user");
    }

    //        this.jdbcContext.executeSql("delete from user"); jdbc템플릿으로 삭제
/*  분리로 인해 삭제
       jdbcContext.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        // Query문 작성
                        return connection.prepareStatement("delete from user");
                    }

                }
        );
        *//*StatementStrategy st = new DeleteAllStrategy();
        jdbcContextWithStatementStrategy(st);*//*
        //jdbcContextWithStatementStrategy(new DeleteAllStrategy()); 한줄로 표현가능
    }*/
/*    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            StatementStrategy statementStrategy = new DeleteAllStrategy();
            ps = statementStrategy.makePreparedStatement(c);


            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.executeUpdate();

        ps.close();
        c.close();
    }*/

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) from user;", Integer.class);
    }

/*        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from user");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                }
            }
        }*/


//        throw new NullPointerException();
      /*  ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
    }*/

    public List<User> getAll() {
        String sql = "select * from user order by id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    /*        RowMapper<User> rowMapper = new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User(rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("password"));
                    return user;
                }
            };
            return this.jdbcTemplate.query(sql, rowMapper);
        }*/
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        userDao.add(new User());
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }

}