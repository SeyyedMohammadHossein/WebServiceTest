package utils;

public class Book {
    private String name;
    private String author;
    private String ISBN;

    private String hasReserved;

    public Book(String name, String author, String ISBN, String hasReserved) {
        setName(name);
        setAuthor(author);
        setISBN(ISBN);
        setHasReserved(hasReserved);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String toString(){
        return this.getISBN() + " " + this.getName() + " " + this.getAuthor() + " " + this.isHasReserved();
    }

    public String isHasReserved() {
        return hasReserved;
    }

    public void setHasReserved(String hasReserved) {
        this.hasReserved = hasReserved;
    }
}
