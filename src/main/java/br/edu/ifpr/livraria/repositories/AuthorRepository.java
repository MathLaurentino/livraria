package br.edu.ifpr.livraria.repositories;

import br.edu.ifpr.livraria.connection.ConnectionFactory;
import br.edu.ifpr.livraria.exceptions.DatabaseException;
import br.edu.ifpr.livraria.models.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

    private final Connection connection;

    public AuthorRepository() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        String sql = "SELECT id, name FROM author";

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");

                Author author = new Author(id, name);
                authors.add(author);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return authors;
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO author (name) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Autor adicionado com sucesso: " + author.getName());

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String generatedId = generatedKeys.getString(1);
                        author.setId(generatedId);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteAuthor(String authorId) {
        String sql = "DELETE FROM author WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, authorId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Autor excluído: " + authorId);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
