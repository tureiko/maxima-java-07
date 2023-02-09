package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:database.properties")
public class SpringConfig {

    @Autowired private Environment environment;

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl((environment.getProperty("db.url")));
        config.setDriverClassName((environment.getProperty("db.driver")));
        return new HikariDataSource(config);
    }

    @Bean("rowmapper")
    public RowMapper<Cat> catRowMapper (){
        return (rs, rowNum) ->  new Cat(
                rs.getString("name"),
                rs.getInt("weight"),
                rs.getBoolean("isAngry"),
                rs.getLong("id")
                );
    }
}
