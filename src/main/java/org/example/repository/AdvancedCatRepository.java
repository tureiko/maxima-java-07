package org.example.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;


public class AdvancedCatRepository implements CatRepository {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        String propertiesPath = "application.properties";
        Properties dbProps = new Properties();
        try {
            dbProps.load(new FileInputStream(propertiesPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String dbUrl = dbProps.getProperty("db.url");
        String dbDriver = dbProps.getProperty("db.driver");

        config.setJdbcUrl(dbUrl);
        config.setDriverClassName(dbDriver);

        ds = new HikariDataSource(config);
    }

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

    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS cats (id INT, NAME VARCHAR(45), Weight INT, isAngry BIT)";

    @Override
    public boolean create(Cat element) {
        try {
            Connection connection = ds.getConnection();
            System.out.println("Соединение с БД выполнено");

            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_CREATE);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS cats (id INT, NAME VARCHAR(45), Weight INT, isAngry BIT)");
            statement.executeUpdate((String.format("INSERT INTO cats(name, weight, isAngry, id) VALUES ('%s', %d,'%s', %d)"
                    , element.getName(), element.getWeight(), element.isAngry(), element.getId())));
            ResultSet result = statement.executeQuery("SELECT * FROM cats");
            while (result.next()) {
                Cat cat = catRowMapper.apply(result);
                String template = (cat.isAngry() ? "Сердитый " : "Добродушный ") + "кот %s весом %d кг.";
                System.out.println(String.format(template, cat.getName(), cat.getWeight(), cat.isAngry()));
            }

            connection.close();
            System.out.println("Отключение от БД выполнено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");

        }
        return false;
    }

    @Override
    public Cat read(Long id) {
        try {
            Connection connection = ds.getConnection();
            System.out.println("Соединение с БД выполнено");

            Statement statement = connection.createStatement();
            String query = (String.format("SELECT * FROM cats WHERE id=%d", id));
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Cat cat = catRowMapper.apply(result);
                String template = (cat.isAngry() ? "Сердитый " : "Добродушный ") + "кот %s весом %d кг.";
                System.out.println(String.format(template, cat.getName(), cat.getWeight(), cat.isAngry()));
            }

            connection.close();
            System.out.println("Отключение от БД выполнено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }

        return null;
    }

    @Override
    public int update(Long id, Cat element) {
        try {
            Connection connection = ds.getConnection();

            String sql = "UPDATE cats SET name=?, weight=?, isAngry=?  WHERE id = ?";

            PreparedStatement preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, element.getName());
            preStatement.setInt(2, element.getWeight());
            preStatement.setBoolean(3, element.isAngry());
            preStatement.setLong(4, id);

            int rows = preStatement.executeUpdate();
            System.out.println("Обновлено записей: " + rows);

            connection.close();
            System.out.println("Отключение от БД выполнено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }
        return 0;
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = ds.getConnection();
            String sql = "DELETE FROM cats WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Удалено записей: " + rows);
            connection.close();
            System.out.println("Отключение от БД выполнено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }
    }

    @Override
    public List<Cat> findAll() {
        List<Cat> cats = new ArrayList<>();

        try {
            Connection connection = ds.getConnection();
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
            System.out.println("Отключение от БД выполнено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }
        return null;
    }
}
