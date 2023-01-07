package org.example;

import java.sql.*;


public class App {

    public static final String DB_URL = "jdbc:h2:mem:test";   // тестовый сервер
    public static final String DB_DRIVER = "org.h2.Driver";

    public static void main(String[] args) throws Exception {


        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД выполнено");

            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE cats (NAME VARCHAR(45), Weight INT)");
            statement.executeUpdate("INSERT INTO cats(name, weight) VALUES ('Мурзик', 10)");
            statement.executeUpdate("INSERT INTO cats(name, weight) VALUES ('Рамзес', 2)");
            statement.executeUpdate("INSERT INTO cats(name, weight) VALUES ('Эдуард', 5)");
            statement.executeUpdate("INSERT INTO cats(name, weight) VALUES ('Эдуард', 7)");

            statement.executeUpdate("ALTER TABLE cats ADD isAngry BIT");

            int rows = statement.executeUpdate("UPDATE cats SET name='Карл' WHERE name = 'Эдуард'");
            System.out.println("Обновлено записей:" + rows);

            ResultSet result = statement.executeQuery("SELECT * FROM cats");
            while (result.next()) {
                String name = result.getString("name");
                int weight = result.getInt("weight");
                boolean isAngry = result.getBoolean("isAngry");
                String template = (isAngry ? "Сердитый" : "Добродушный ") + "кот %s весом %d кг.";
                System.out.println(String.format(template, name, weight, isAngry));
            }

            connection.close();
            System.out.println("Отключение от БД выполнено");

        } catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Нет драйвера БД !!");
        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }

    }
}