package java.com.line.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class UserDaoFactory {

/*    @Bean
    DataSource dataSource() {
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(env.get("DB_HOST"));
        dataSource.setUsername(env.get("DB_USER"));
        dataSource.setPassword(env.get("DB_PASSWROD"));
        return dataSource;
    }*/

    @Bean
    UserDao awsUserDao(){
        return new UserDao(h2DataSource());
    }
/*    public UserDao awsUserDao() {
        return new UserDao(new AwsConnectionMaker());
        // 한문장으로 줄이면
        *//*        AwsConnectionMaker awsConnectionMaker = new AwsConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;*//*
    }*/
//
//    @Bean
//    public UserDao localUserDao() {
//        return new UserDao(localDataSource());
///*        return new UserDao(new LocalConnectionMaker());
//        *//*        UserDao userDao = new UserDao(new LocalConnectionMaker());
//        return userDao;*/
//    }
//
//    @Bean
//    DataSource awsDataSource() {
//        Map<String, String> env = System.getenv();
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
//        dataSource.setUrl(env.get("DB_HOST"));
//        dataSource.setUsername(env.get("DB_USER"));
//        dataSource.setPassword(env.get("DB_PASSWORD"));
//        return dataSource;
//    }
//    @Bean
//    DataSource localDataSource() {
//        Map<String, String> env = System.getenv();
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
//        dataSource.setUrl("localhost");
//        dataSource.setUsername("root");
//        dataSource.setPassword("12345678");
//        return dataSource;
//    }

    @Bean
    DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .setName("likelion-db;MODE=MySQL")
                .addScript("classpath:jdbc/schema.sql")
                .build();
    }

}
