package org.example.repository;

import org.example.model.Cat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleCatRepository implements CatRepository {
    public static final String DB_DRIVER = "org.h2.Driver";
     public static final String TABLE = "CREATE TABLE IF NOT EXISTS %s (Name VARCHAR(45), Weight INT, isAngry BIT, id INT)";

    private String tableName;
    private String DB_URL;

    public SimpleCatRepository(String tableName, String DB_URL) {
        this.tableName = tableName;
        this.DB_URL = DB_URL;
    }

    public SimpleCatRepository() {
    }


    @Override
    public boolean create(Cat element) {
        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД выполнено");
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format(TABLE, tableName));
            statement.executeUpdate((String.format("INSERT INTO cats(name, weight, isAngry, id) VALUES ('%s', %d,'%s', %d)"
                    , element.getName(), element.getWeight(), element.isAngry(), element.getId())));
            ResultSet result = statement.executeQuery("SELECT * FROM cats");
            int row = 0;
            while (result.next()) {
                String name = result.getString("name");
                int weight = result.getInt("weight");
                boolean isAngry = result.getBoolean("isAngry");
                long id = result.getLong("id");
                System.out.println(name + " " + weight + " " + isAngry + " " + id);
                row = result.getRow();
                System.out.println("Записей обновлено: " + row);

            }

            System.out.println("Записей обновлено: " + row);
            connection.close();
            System.out.println("Отключение от БД выполнено");
            if (row > 0) return true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Нет драйвера БД !!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");

        }

        return false;
    }

    @Override
    public Cat read(Long id) {
        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД выполнено");
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format(TABLE, tableName));
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Мурзик',10,true,1L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Рамзес',3,false,2L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Эдуард',5,true,3L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Карл III',7,false,4L)");
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM cats WHERE id=%d", id));
            while (result.next()) {
                String name = result.getString("name");
                int weight = result.getInt("weight");
                boolean isAngry = result.getBoolean("isAngry");
                Cat cat = new Cat(name, weight, isAngry, id);
                System.out.println(cat.toString());
                connection.close();
                System.out.println("Отключение от БД выполнено");
                return cat;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int update(Long id, Cat element) {
        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД выполнено");
            Statement statement = connection.createStatement();

            statement.executeUpdate(String.format(TABLE, tableName));
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Мурзик',10,true,1L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Рамзес',3,false,2L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Эдуард',5,true,3L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Карл III',7,false,4L)");
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM cats WHERE id=%d", id));
            while (result.next()) {
                String name = result.getString("name");
                int weight = result.getInt("weight");
                boolean isAngry = result.getBoolean("isAngry");
                String request = String.format("UPDATE cats SET name='%s', weight=%d, isAngry='%s', id=%d WHERE id = %d",
                        element.getName(), element.getWeight(), element.isAngry(), element.getId(), id);
                int rows = statement.executeUpdate(request);
                System.out.println("Обновлено записей:" + rows);
                connection.close();
                System.out.println("Отключение от БД выполнено");
                return rows;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format(TABLE, tableName));
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Мурзик',10,true,1L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Рамзес',3,false,2L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Эдуард',5,true,3L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Карл III',7,false,4L)");
            int rows = statement.executeUpdate(String.format("DELETE FROM cats WHERE id=%d", id));
            System.out.println("Удалено записей:" + rows);
            System.out.println("Обновлено записей:" + rows);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cat> findAll() {
        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД выполнено");
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format(TABLE, tableName));
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Мурзик',10,true,1L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Рамзес',3,false,2L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Эдуард',5,true,3L)");
            statement.executeUpdate("INSERT INTO cats(name, weight, isAngry, id) VALUES ('Карл III',7,false,4L)");
            List<Cat> cats = new ArrayList<>();

            ResultSet result1 = statement.executeQuery("SELECT * FROM cats");
            while (result1.next()) {
                String name = result1.getString("name");
                int weight = result1.getInt("weight");
                boolean isAngry = result1.getBoolean("isAngry");
                Long id = result1.getLong("id");
                Cat cat = new Cat(name, weight, isAngry, id);
                System.out.println(cat.toString());
                cats.add(cat);

            }
            System.out.println(cats.size());
            return cats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
