package br.edu.ifpr.livraria.controllers;


import br.edu.ifpr.livraria.models.Book;
import br.edu.ifpr.livraria.repositories.AuthorRepository;
import br.edu.ifpr.livraria.repositories.BookRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BooksController extends HttpServlet {

    BookRepository repository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = repository.getAllBooks();
        req.setAttribute("books", books);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/books.jsp");
        dispatcher.forward(req, resp);
    }

}
