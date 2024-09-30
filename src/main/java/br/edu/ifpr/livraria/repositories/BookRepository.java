package br.edu.ifpr.livraria.repositories;

import br.edu.ifpr.livraria.connection.ConnectionFactory;
import br.edu.ifpr.livraria.exceptions.DatabaseException;
import br.edu.ifpr.livraria.models.Author;
import br.edu.ifpr.livraria.models.Book;
import br.edu.ifpr.livraria.models.BookStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    Connection connection;


    public BookRepository() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT b.id, b.name, b.date, b.status, a.name as author_name " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id";

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                LocalDate date = result.getDate("date").toLocalDate();
                BookStatus status = BookStatus.valueOf(result.getString("status").toUpperCase());
                String authorName = result.getString("author_name");

                Author author = new Author(null, authorName); // Relacionamento simples
                Book book = new Book(id, name, date, status, author);
                books.add(book);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return books;
    }

    public void updateBookStatus(String bookId, BookStatus newStatus) {
        String sql = "UPDATE book SET status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus.name().toLowerCase());
            statement.setString(2, bookId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Status atualizado para o livro ID: " + bookId);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteBook(String bookId) {
        String sql = "DELETE FROM book WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Livro excluído: " + bookId);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO book (name, date, status, author_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getName());
            statement.setDate(2, Date.valueOf(book.getDate()));
            statement.setString(3, book.getStatus().name().toLowerCase());
            statement.setString(4, book.getAuthor().getId());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Livro adicionado com sucesso: " + book.getName());

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String generatedId = generatedKeys.getString(1);
                        book.setId(generatedId);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Book getById(String id) {
        Book book = null;
        String sql = "SELECT b.*, a.name AS author_name FROM book b " +
                "LEFT JOIN author a ON b.author_id = a.id WHERE b.id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getString("id"));
                book.setName(resultSet.getString("name"));
                book.setDate(resultSet.getDate("date").toLocalDate());
                book.setStatus(BookStatus.valueOf(resultSet.getString("status").toUpperCase()));

                Author author = new Author();
                author.setId(resultSet.getString("author_id"));
                author.setName(resultSet.getString("author_name"));
                book.setAuthor(author);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return book;
    }

    public void update(Book book) {
        String sql = "UPDATE book SET name = ?, date = ?, status = ?, author_id = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getName());
            statement.setDate(2, Date.valueOf(book.getDate()));
            statement.setString(3, book.getStatus().name().toLowerCase());
            statement.setString(4, book.getAuthor().getId());
            statement.setString(5, book.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
