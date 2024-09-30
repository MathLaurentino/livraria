package br.edu.ifpr.livraria.validators;

import br.edu.ifpr.livraria.models.BookStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BookValidator {

    public Map<String, String> validate(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();

        String name = req.getParameter("field_name");
        String dateString = req.getParameter("field_date");
        String statusString = req.getParameter("field_status");
        String authorId = req.getParameter("field_authorId");

        if (name == null || name.isEmpty()) {
            errors.put("name", "Nome é obrigatório.");
        }

        try {
            LocalDate.parse(dateString);
        } catch (Exception e) {
            errors.put("date", "Data inválida.");
        }

        if (statusString == null || statusString.isEmpty()) {
            errors.put("status", "Status é obrigatório.");
        } else {
            try {
                BookStatus.valueOf(statusString.toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.put("status", "Status inválido.");
            }
        }

        if (authorId == null || authorId.isEmpty()) {
            errors.put("authorId", "Autor é obrigatório.");
        }

        return errors;
    }
}
