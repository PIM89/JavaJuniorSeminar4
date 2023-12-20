package org.example;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.*;


public class HomeWorkTask2 {
    public static void main(String[] args) {
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

        // 2.2 Создать Session и сохранить в таблицу 10 книг
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        createTenBook(sessionFactory, books);

        // 2.3 Выгрузить список книг какого-то автора
        reqByAuthor(sessionFactory, "Михаил Булгаков");
    }

    public static void createTenBook(SessionFactory sessionFactory, Map<String, String> books) {

        System.out.println("Добавление информации о книгах в БД...");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            for (Map.Entry<String, String> entry : books.entrySet()) {
                Author author = new Author(entry.getValue());

                Book book = new Book();
                book.setName(entry.getKey());
                book.setAuthor(author);

                session.persist(author);
                session.persist(book);
                session.flush();
            }
            session.getTransaction().commit();
        }
    }

    public static void reqByAuthor(SessionFactory sessionFactory, String authorName) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT b FROM Book b WHERE author.name = " + "'" + authorName + "'";
            Query<Book> query = session.createQuery(sql, Book.class);
            query.stream().forEach(System.out::println);
        }
    }
}

// 2.1 Описать сущность Book
@Entity
@Table(name = "book")
@Getter
@Setter
class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Author author;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}

@Entity
@Table(name = "author")
@Getter
@Setter
class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn (name="id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorID=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}