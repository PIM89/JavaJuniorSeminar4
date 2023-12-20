package org.example;


import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeWorkTask2 {
    public static void main(String[] args) {
        // 2.2 Создать Session и сохранить в таблицу 10 книг
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        createTenBook(sessionFactory);

        // 2.3 Выгрузить список книг какого-то автора
        reqByAuthor(sessionFactory, "Михаил Булгаков");
    }

    public static void createTenBook(SessionFactory sessionFactory) {
        Map<String, String> books = new HashMap<>();
        books.put("Прислуга", "Кэтрин Стокетт");
        books.put("Возвращение государя", "Джон Р. Р. Толкиен");
        books.put("Граф Монте-Кристо", "Александр Дюма");
        books.put("Властелин колец", "Джон Р. Р. Толкин");
        books.put("Крестный отец", "Марио Пьюзо");
        books.put("Вторая жизнь Уве", "Фредрик Бакман");
        books.put("Десять негритят", "Агата Кристи");
        books.put("Безумный корабль", "Робин Хобб");
        books.put("Цветы для Элджернона", "Дэниел Киз");
        books.put("Записки юного врача", "Михаил Булгаков");
        System.out.println("Добавление информации о книгах в БД...");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Map.Entry<String, String> entry : books.entrySet()) {
                Book book = new Book();
                book.setName(entry.getKey());
                book.setAuthor(entry.getValue());
                session.persist(book);
                session.flush();
            }
            session.getTransaction().commit();
        }
    }

    public static void reqByAuthor(SessionFactory sessionFactory, String author) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "select b from Book b where author = " + "'" + author + "'";
            Query<Book> query = session.createQuery(sql, Book.class);
            List resultList = query.getResultList();
            for (Object o : resultList) {
                System.out.println(o);
            }
        }
    }
}

// 2.1 Описать сущность Book
@Entity
@Table(name = "book")
class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "author")
    private String author;
    @Column(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

