package org.example;

import java.sql.*;


class HomeWorkTask1 {
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Seminar_4";
    static final String USER = "pim";
    static final String PASSWORD = "12345";

    public static void main(String[] args) {
        System.out.println("Подключение к БД...");
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            // 1.1 Создать таблицу book с колонками id bigint, name varchar, author varchar
            createTable(connection);
            // 1.2 Добавить в таблицу 10 книг
            addBooks(connection);
            // 1.3 Сделать запрос select from book where author = 'какое-то имя' и прочитать его с помощью ResultSet
            reqByAuthor(connection, "Стивен Кинг");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(Connection connection) {
        System.out.println("Создание таблицы в БД...");
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS book " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45), " +
                    "author VARCHAR (45))";
            statement.execute(sql);
            System.out.println("Таблица создана!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBooks(Connection connection) {
        System.out.println("Добавление информации о книгах в БД...");
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO book (name, author) " +
                    "VALUES ('Война и мир', 'Лев Николаевич Толстой'), " +
                    "('Зеленая миля', 'Стивен Кинг'), " +
                    "('Унесенные ветром', 'Маргарет Митчелл'), " +
                    "('Шерлок Холмс', 'Артур Конан Дойл'), " +
                    "('Гарри Поттер', 'Мишель Пейвер'), " +
                    "('Граф Монте-Кристо', 'Александр Дюма'), " +
                    "('Буря мечей', 'Джордж Мартин'), " +
                    "('В списках не значился', 'Борис Васильев'), " +
                    "('Пустая могила', 'Джонатан Страуд'), " +
                    "('Продажное королевство', 'Ли Бардуго') ";
            statement.execute(sql);
            System.out.println("Информация о книгах добавлена!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void reqByAuthor(Connection connection, String authorName) {
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM book WHERE author = " + "'" + authorName + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                System.out.println(id + " " + name + " " + author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
