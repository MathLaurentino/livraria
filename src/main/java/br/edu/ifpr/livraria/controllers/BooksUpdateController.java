package br.edu.ifpr.livraria.controllers;

import br.edu.ifpr.livraria.models.Author;
import br.edu.ifpr.livraria.models.Book;
import br.edu.ifpr.livraria.models.BookStatus;
import br.edu.ifpr.livraria.repositories.AuthorRepository;
import br.edu.ifpr.livraria.repositories.BookRepository;
import br.edu.ifpr.livraria.validators.BookValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@WebServlet("/books/update")
public class BooksUpdateController extends HttpServlet {

    BookRepository repository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        List<Author> authors = authorRepository.getAllAuthors();
        req.setAttribute("authors", authors);

        Book book = repository.getById(id);
        req.setAttribute("book", book);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/books-update.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookValidator validator = new BookValidator();
        Map<String, String> errors = validator.validate(req);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            doGet(req, resp);
            return;
        }

        String id = req.getParameter("field_id");
        String name = req.getParameter("field_name");
        LocalDate date = LocalDate.parse(req.getParameter("field_date"));
        String statusString = req.getParameter("field_status");
        String authorId = req.getParameter("field_authorId");

        BookStatus status = BookStatus.valueOf(statusString.toUpperCase());

        Author author = new Author();
        author.setId(authorId);

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setDate(date);
        book.setStatus(status);
        book.setAuthor(author);

        repository.update(book);

        resp.sendRedirect(req.getContextPath() + "/books");
    }

}
