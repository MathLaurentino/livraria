package br.edu.ifpr.livraria.models;

import java.time.LocalDate;

public class Book {
    private String id;
    private String name;
    private LocalDate date;
    private BookStatus status;
    private Author author;

    public Book() {

    }

    public Book(String id, String name, LocalDate date, BookStatus status, Author author) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.status = status;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
