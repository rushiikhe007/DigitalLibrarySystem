package assignment;

import java.util.*;

/**
 * Represents a Book with unique ID, title, author, genre, and availability status.
 */
class Book {
    private final String bookId;
    private String title;
    private String author;
    private String genre;
    private String availabilityStatus;

    /**
     * Constructs a Book object with the given details.
     */
    public Book(String bookId, String title, String author, String genre, String availabilityStatus) {
        if (bookId == null || bookId.isEmpty()) throw new IllegalArgumentException("Book ID cannot be empty.");
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty.");
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("Author cannot be empty.");
        if (!availabilityStatus.equalsIgnoreCase("Available") && !availabilityStatus.equalsIgnoreCase("Checked Out")) {
            throw new IllegalArgumentException("Availability status must be 'Available' or 'Checked Out'.");
        }
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availabilityStatus = availabilityStatus;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getAvailabilityStatus() { return availabilityStatus; }

    /**
     * Updates the book details if new values are provided.
     */
    public void updateBook(String title, String author, String genre, String availabilityStatus) {
        if (title != null && !title.isEmpty()) this.title = title;
        if (author != null && !author.isEmpty()) this.author = author;
        if (genre != null && !genre.isEmpty()) this.genre = genre;
        if (availabilityStatus != null && (availabilityStatus.equalsIgnoreCase("Available") || availabilityStatus.equalsIgnoreCase("Checked Out"))) {
            this.availabilityStatus = availabilityStatus;
        }
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Availability: " + availabilityStatus;
    }
}

/**
 * Manages library books with add, update, search, delete, and list functionalities.
 */
class Library {
    private final Map<String, Book> books = new HashMap<>();

    /**
     * Adds a new book to the library.
     */
    public void addBook(Book book) {
        if (books.containsKey(book.getBookId())) {
            throw new IllegalArgumentException("Book ID already exists.");
        }
        books.put(book.getBookId(), book);
    }

    /**
     * Retrieves a list of all books in the library.
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    /**
     * Searches for a book by ID or title.
     */
    public Book searchBook(String query) {
        return books.values().stream()
                .filter(book -> book.getBookId().equalsIgnoreCase(query) || book.getTitle().equalsIgnoreCase(query))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates book details by ID if found.
     */
    public void updateBook(String bookId, String title, String author, String genre, String availabilityStatus) {
        Book book = books.get(bookId);
        if (book == null) throw new IllegalArgumentException("Book ID not found.");
        book.updateBook(title, author, genre, availabilityStatus);
    }

    /**
     * Deletes a book from the library using its ID.
     */
    public void deleteBook(String bookId) {
        if (!books.containsKey(bookId)) throw new IllegalArgumentException("Book ID not found.");
        books.remove(bookId);
    }
}

/**
 * Entry point of the Library Management System.
 */
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addBook(library, scanner);
                    case 2 -> viewBooks(library);
                    case 3 -> searchBook(library, scanner);
                    case 4 -> updateBook(library, scanner);
                    case 5 -> deleteBook(library, scanner);
                    case 6 -> {
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter Availability Status (Available/Checked Out): ");
        String availabilityStatus = scanner.nextLine();
        library.addBook(new Book(bookId, title, author, genre, availabilityStatus));
        System.out.println("Book added successfully.");
    }

    private static void viewBooks(Library library) {
        List<Book> allBooks = library.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            allBooks.forEach(System.out::println);
        }
    }

    private static void searchBook(Library library, Scanner scanner) {
        System.out.print("Enter Book ID or Title: ");
        String query = scanner.nextLine();
        Book foundBook = library.searchBook(query);
        System.out.println(foundBook != null ? foundBook : "Book not found.");
    }

    private static void updateBook(Library library, Scanner scanner) {
        System.out.print("Enter Book ID to update: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter new Title (or press Enter to keep current): ");
        String title = scanner.nextLine();
        System.out.print("Enter new Author (or press Enter to keep current): ");
        String author = scanner.nextLine();
        System.out.print("Enter new Genre (or press Enter to keep current): ");
        String genre = scanner.nextLine();
        System.out.print("Enter new Availability Status (or press Enter to keep current): ");
        String status = scanner.nextLine();
        library.updateBook(bookId, title, author, genre, status);
        System.out.println("Book updated successfully.");
    }

    private static void deleteBook(Library library, Scanner scanner) {
        System.out.print("Enter Book ID to delete: ");
        String bookId = scanner.nextLine();
        library.deleteBook(bookId);
        System.out.println("Book deleted successfully.");
    }
}
