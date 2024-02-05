package com.example.demo3;

import utils.Book;
import utils.User;
import Connection.SqlConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private File usersInfo;
    private File booksInfo;
    private FileWriter userWriter;
    private FileWriter bookWriter;
    private ArrayList<User> users;
    private ArrayList<Book> books;
    private Scanner scanner;

    SqlConnection sql;

    public Library(/*String usersFileAddress, String booksFileAddress, */String sqlAddress, String sqlUsername, String sqlPassword) throws IOException {
        users = new ArrayList<>();
        books = new ArrayList<>();/*
        usersInfo = new File(usersFileAddress);
        booksInfo = new File(booksFileAddress);
        System.out.println(readInfoFromFile());
        userWriter = new FileWriter(usersInfo);
        bookWriter = new FileWriter(booksInfo);*/
        sql = new SqlConnection(sqlAddress, sqlUsername, sqlPassword);
        System.out.println(readInfoFromSql());
    }

    public String readInfoFromFile() throws FileNotFoundException {
        try {
            List<String> lines = Files.readAllLines(booksInfo.toPath());
            lines.forEach(s -> {
                books.add(new Book(s.split(" ")[1], s.split(" ")[2]
                        , s.split(" ")[0], s.split(" ")[3]));
            });
            /*scanner = new Scanner(booksInfo);
            while (scanner.hasNext()) {
                input = scanner.nextLine().split(" ");
                books.add(new Book(input[1], input[2], input[0], input[3]));
            }*/
            String[] input;
            scanner = new Scanner(usersInfo);
            String temp;
            while (scanner.hasNext()) {
                input = scanner.nextLine().split(" ");
                users.add(new User(input[0], input[1], input[2]));
                temp = scanner.nextLine();
                while (!temp.equals("------------------")) {
                    input = temp.split(" ");
                    users.get(users.size() - 1).addToBorrowedList(new Book(input[5], input[6], input[4], input[7]));
                    temp = scanner.nextLine();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "All data read successfully!";
    }

    public String readInfoFromSql() {
        try {
            ResultSet booksResultSet = sql.getResultSet("books");
            while (booksResultSet.next()) {
                String ISBN = booksResultSet.getString("ISBN");
                String name = booksResultSet.getString("name");
                String author = booksResultSet.getString("author");
                String hasReserved = booksResultSet.getString("hasReserved");
                books.add(new Book(name, author, ISBN, "0"));
            }
            ResultSet usersResultSet = sql.getResultSet("users");
            while (usersResultSet.next()) {
                String name = usersResultSet.getString("name");
                String email = usersResultSet.getString("email");
                String password = usersResultSet.getString("password");
                users.add(new User(name, email, password));
            }
            ResultSet borrowedBooksResultSet = sql.getResultSet("borrowedbooks");
            while (borrowedBooksResultSet.next()) {
                String ISBN = borrowedBooksResultSet.getString("ISBN");
                int id = Integer.parseInt(borrowedBooksResultSet.getString("userId"));
                borrowBook(ISBN, id, true);
            }
            return "reading successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addUser(User user) {
        try {
            for (User tempUser : users) {
                if (tempUser.getEmail().equals(user.getEmail()))
                    return "duplicate value for email";
            }
            users.add(user);
            sql.addUserToDatabase(user.getName(), user.getEmail(), user.getPassword());
            return "user successfully created " + users.size();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteUser(int id) {
        if (users.size() < id)
            return "user not found";
        users.remove(id - 1);
        return "user deleted";
    }

    public User getUser(int id) {
        if (users.size() < id)
            return null;
        return users.get(id - 1);
    }

    public String getAllUsers() {
        if (users.size() == 0)
            return "no user exists";
        String output = "";
        for (int i = 0; i < users.size(); i++) {
            output += i + 1 + " " + users.get(i) + "\n";
        }
        return output;
    }

    public String addBook(Book book) throws IOException {
        try {
            for (Book tempBook : books) {
                if (tempBook.getISBN().equals(book.getISBN()))
                    return "DUPLICATE BOOK-ID";
            }
            books.add(book);
            sql.addBookToDatabase(book.getISBN(), book.getName(), book.getAuthor(), book.isHasReserved());
            return "BOOK CREATED";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteBook(String ISBN) {
        for (Book book : books)
            if (book.getISBN().equals(ISBN)) {
                books.remove(book);
                return "book deleted";
            }
        return "book not found";
    }

    public Book getBook(String ISBN) {
        for (Book book : books)
            if (book.getISBN().equals(ISBN))
                return book;
        return null;
    }

    public String getAllBook() {
        if (books.size() == 0)
            return "no book exists";
        String output = "";
        for (Book book : books) {
            output += book + "\n";
        }
        return output;
    }

    public String borrowBook(String ISBN, int id, boolean isReading) {
        String output = "";
        try {
            Book book = getBook(ISBN);
            if (book == null)
                throw new RuntimeException("Book not found!");
            User user = getUser(id);
            if (user == null)
                throw new RuntimeException("User not found!");
            if (user.getBorrowedList().contains(book)) {
                return "This book already exists in the desired user's deposit list!";
            }
            if (!book.isHasReserved().equals("0")) {
                return "This book has already been reserved!";
            }
            book.setHasReserved(String.valueOf(id));
            user.addToBorrowedList(book);
            if (!isReading)
                sql.addBorrowedBookToDatabase(ISBN, id);
            output = "The book (" + book.getName() + " - " + book.getISBN() + ") "
                    + "was added to user's (" + user.getName() + " - " + id + ") borrowed list.";
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String returnBook(int id, String ISBN) {
        String output = "";
        try {
            Book book = getBook(ISBN);
            if (book == null)
                throw new RuntimeException("Book not found!");
            User user = getUser(id);
            if (user == null)
                throw new RuntimeException("User not found!");
            if (book.isHasReserved().equals("0")) {
                throw new RuntimeException("This book has not been reserved by anyone.");
            }
            book.setHasReserved("0");
            sql.returnBookUpdateHasReservedToDatabase(ISBN);
            user.removeFromBorrowedList(book);
            sql.removeBookFromBorrowedBooksList(ISBN, id);
            output = "The book (" + book.getName() + " - " + book.getISBN() + ")"
                    + "was returned from user's (" + user.getName() + " - " + id + ") to library.";
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void closeWriters() throws IOException {
        for (User user : users) {
            userWriter.write(user.toString() + "\n");
        }
        for (Book book : books) {
            bookWriter.write(book.toString() + "\n");
        }
        userWriter.close();
        bookWriter.close();
    }
}
