package org.example.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AdvancedCatRepository implements CatRepository {
    //private static HikariConfig config = new HikariConfig(
    //    "datasource.properties" );
    private String DB_DRIVER;
    private String DB_URL;

    public AdvancedCatRepository(String DB_DRIVER, String DB_URL) {
        this.DB_DRIVER = DB_DRIVER;
        this.DB_URL = DB_URL;
    }

    @Override
    public boolean create(Cat element) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            System.out.println("Соединение с БД выполнено");

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS cats (id INT, NAME VARCHAR(45), Weight INT, isAngry BIT)");
            statement.executeUpdate((String.format("INSERT INTO cats(name, weight, isAngry, id) VALUES ('%s', %d,'%s', %d)"
                    , element.getName(), element.getWeight(), element.isAngry(), element.getId())));

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");

        }
        return false;
    }

    @Override
    public Cat read(Long id) {

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            System.out.println("Соединение с БД выполнено");

            Function<ResultSet, Cat> catRowMapper = rs -> {
                try {
                    return new Cat(
                            rs.getString("name"),
                            rs.getInt("weight"),
                            rs.getBoolean("isAngry"),
                            rs.getLong("id")
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };


            Statement statement = connection.createStatement();
            String query = (String.format("SELECT * FROM cats WHERE id=%d", id));
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Cat cat = catRowMapper.apply(result);
                String template = (cat.isAngry() ? "Сердитый " : "Добродушный ") + "кот %s весом %d кг.";
                System.out.println(String.format(template, cat.getName(), cat.getWeight(), cat.isAngry()));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }

        return null;
    }

    @Override
    public int update(Long id, Cat element) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            System.out.println("Соединение с БД выполнено");


            String sql = "UPDATE cats SET name=?, weight=?, isAngry=?  WHERE id = ?";

            PreparedStatement preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, element.getName());
            preStatement.setInt(2, element.getWeight());
            preStatement.setBoolean(3, element.isAngry());
            preStatement.setLong(4, id);

            int rows = preStatement.executeUpdate();
            System.out.println("Обновлено записей: " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }
        return 0;
    }

    @Override
    public void delete(Long id) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            System.out.println("Соединение с БД выполнено");

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cats WHERE id=?");
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Удалено записей: " + rows);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }
    }

    @Override
    public List<Cat> findAll() {
        List<Cat> cats = new ArrayList<>();
        Function<ResultSet, Cat> catRowMapper = rs -> {
            try {
                return new Cat(
                        rs.getString("name"),
                        rs.getInt("weight"),
                        rs.getBoolean("isAngry"),
                        rs.getLong("id")
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        try {

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            System.out.println("Соединение с БД выполнено");
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM cats");
            while (result.next()) {
                Cat cat = catRowMapper.apply(result);
                String template = (cat.isAngry() ? "Сердитый " : "Добродушный ") + "кот %s весом %d кг.";
                System.out.println(String.format(template, cat.getName(), cat.getWeight(), cat.isAngry()));
                cats.add(cat);
            }
            System.out.println(cats);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }
        return null;
    }
}
