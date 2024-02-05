package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     This class consists of three fields: name, email address, password and borrowed books list.<br>
 *     And also from several methods, the input parameters and information of each one will be explained in the relevant section.
 * </p>
 */
public class User {
    private String name;
    private String email;
    private String password;

    /**
     * In this list, the books that the user has borrowed from the library are kept.
     */
    List<Book> borrowedBooks;

    /**
     * @param name Each username must be a string of letters.
     * @param email Each user's email must be in a valid format.
     * @param password Each user's password can be a string consisting of letters and numbers
     */
    public User(String name, String email, String password){
        setName(name);
        setEmail(email);
        setPassword(password);
        borrowedBooks = new ArrayList<>();
    }

    /**
     * @return String (This method returns the value of the desired username)
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The input parameter of this method is the username (a string consisting of letters).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String (This method returns the value of the desired email address)
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The input parameter of this method is the email address (a string consisting of [emailAddress]@[subdomain].domain).
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String (This method returns the value of the desired password)
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The input parameter of this method is the password (a string consisting of letters or nums).
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param book It takes a book as an input parameter and adds it to the user's borrowed books list
     */
    public void addToBorrowedList(Book book){
        borrowedBooks.add(book);
    }

    /**
     * @param book It takes a book as an input parameter and removes it from the list of books borrowed by the user
     */
    public void removeFromBorrowedList(Book book){
        /*borrowedBooks.forEach(book1 -> {
            if (book1.getISBN().equals(book.getISBN())) {
                borrowedBooks.remove(book1);
                return;
            }
        });*/
        for (Book tempBook : borrowedBooks) {
            if (tempBook.getISBN().equals(book.getISBN())){
                borrowedBooks.remove(tempBook);
                return;
            }
        }
        throw new RuntimeException("Book not found!");
    }

    /**
     * @return (List of book) the list of books borrowed by the user as output
     */
    public List<Book> getBorrowedList(){
        return borrowedBooks;
    }

    /**
     * @return It returns the desired user information in the form of a string as output
     */
    public String toString(){
        String output = this.getName() + " " + this.getEmail() + " " + this.getPassword() + "\n";
        for (Book book : borrowedBooks) {
            output += "    " + book.toString() + "\n";
        }
        output += "------------------";
        return output;
    }
}
