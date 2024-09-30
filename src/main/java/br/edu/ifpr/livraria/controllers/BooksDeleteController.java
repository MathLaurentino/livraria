package br.edu.ifpr.livraria.controllers;

import br.edu.ifpr.livraria.repositories.BookRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/books/delete")
public class BooksDeleteController extends HttpServlet {

    BookRepository repository = new BookRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        repository.deleteBook(id);

        resp.sendRedirect(req.getContextPath() + "/books");
    }
}
