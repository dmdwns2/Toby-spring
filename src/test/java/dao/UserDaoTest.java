package dao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.com.line.dao.UserDao;
import java.com.line.dao.UserDaoFactory;
import java.com.line.domain.User;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;


    UserDao userDao;
    User user1 = new User("1", "kyeonghwan", "1123");
    User user2 = new User("2", "sohyun", "1234");
    User user3 = new User("3", "sujin", "4321");

    @BeforeEach
    void Setup() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        System.out.println("Before Each");
    }

    @Test
    void addAndSelect() throws SQLException {
        String id = "24";
        userDao.add(new User(id, "Rara", "1123"));

        User selectedUser = userDao.findById(id);
        assertEquals("Rara", selectedUser.getName());
    }

    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getId(), user.getId());
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    void count() throws SQLException {

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());

    }

    @Test
    void findById() {
//        assertThrows(CannotGetJdbcConnectionException.class, () -> {
//            userDao.findById("30");
//        });
//        jdbc 테플릿 사용으로 인한 변경
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("30");
        });
    }

    @Test
    @DisplayName("없을 때 빈 리스트 리턴 하느지 , 있을 때 개수만큼 리턴 하는지")
    void getAllTest() throws SQLException {
        userDao.deleteAll();
        List<User> user = userDao.getAll();
        assertEquals(0, user.size());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        user = userDao.getAll();
        assertEquals(3, user.size());

    }
}
