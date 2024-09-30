package br.edu.ifpr.livraria.exceptions;

//não chechada
public class DatabaseIntegrityException extends RuntimeException {

    public DatabaseIntegrityException(String msg){
        super(msg);
    }

}
