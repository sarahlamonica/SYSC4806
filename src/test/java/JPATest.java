import com.amazon.bookstore.Book;
import com.amazon.bookstore.BookStore;
import com.amazon.bookstore.ShoppingCart;

import javax.persistence.*;
import java.util.List;

public class JPATest {
    private static BookStore[] bookstore = new BookStore[]{
            new BookStore("Chapters"),
            new BookStore("Indigo")
    };

    private static Book[] books = new Book[]{
            new Book("Harry Potter", "JK Rowling", "N/A", 123, "HP", "Fantasy", 1),
            new Book("Lord of the Rings", "JRR Tolkien", "N/A", 124, "LOTR", "Fantasy",2)
    };

    private static ShoppingCart[] shoppingcart = new ShoppingCart[]{
            new ShoppingCart()
    };

    public JPATest()
    {

    }

    public void performJPA()
    {
        // Create the EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        for(BookStore b: bookstore){
            em.persist(b);
        }

        for(ShoppingCart s: shoppingcart){
            Book b = new Book("Harry Potter", "JK Rowling", "N/A", 123, "HP", "Fantasy", 1);
            s.addBook(b);
            em.persist(s);
        }

        for(Book bb: books){
            //bb.setBookstore(bookstore[0]);
            bookstore[0].addBook(bb);
            em.persist(bb);
        }

        em.getTransaction().commit();

        Query q1 = em.createQuery("SELECT p FROM Book p");
        Query q2 = em.createQuery("SELECT x FROM BookStore x");
        Query q3 = em.createQuery("SELECT s FROM ShoppingCart s");

        @SuppressWarnings("unchecked")
        List<Book> results = q1.getResultList();

        @SuppressWarnings("unchecked")
        List<BookStore> results2 = q2.getResultList();

        List<ShoppingCart> results3 = q3.getResultList();


        System.out.println("\n   ");
        System.out.println("List of Books\n----------------");

        for (Book p : results) {
            System.out.println(p.getTitle() + " (id=" + p.getId() + ")");
        }

        System.out.println("\n   ");
        System.out.println("List of BookStores\n----------------");

        for (BookStore x : results2) {
            System.out.println(x.getName() + " (id=" + x.getId() + ")");
        }

        System.out.println("\n   ");
        System.out.println("List of ShoppingCart\n----------------");

        for (ShoppingCart s : results3) {
            System.out.println(" (id=" + s.getId() + ")");
        }

        em.close();
        emf.close();

    }

    public static void main(String [] args)
    {
        JPATest b = new JPATest();
        b.performJPA();
    }

}
