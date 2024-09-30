
create database biblioteca;
use biblioteca;

-- Criação da tabela author
create table author (
                        id varchar(36) primary key default (uuid()),
                        name varchar(255) not null
);

-- Criação da tabela book
create table book (
                      id varchar(36) primary key default (uuid()),
                      name varchar(255) not null,
                      date date,
                      status enum('DISPONIVEL', 'EMPRESTADO', 'INDISPONIVEL') not null,
                      author_id varchar(36),
                      foreign key (author_id) references author(id)
);

INSERT INTO author (name) VALUES ('J.K. Rowling');
INSERT INTO author (name) VALUES ('George R.R. Martin');
INSERT INTO author (name) VALUES ('J.R.R. Tolkien');

INSERT INTO book (name, date, status, author_id)
VALUES ('Harry Potter and the Philosopher\'s Stone', '1997-06-26', 'DISPONIVEL',
        (SELECT id FROM author WHERE name = 'J.K. Rowling'));

INSERT INTO book (name, date, status, author_id)
VALUES ('A Game of Thrones', '1996-08-06', 'EMPRESTADO',
        (SELECT id FROM author WHERE name = 'George R.R. Martin'));

INSERT INTO book (name, date, status, author_id)
VALUES ('The Lord of the Rings', '1954-07-29', 'DISPONIVEL',
        (SELECT id FROM author WHERE name = 'J.R.R. Tolkien'));