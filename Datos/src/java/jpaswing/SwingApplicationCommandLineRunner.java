package jpaswing;

import jpaswing.repository.BookRepository;
import jpaswing.ui.BookUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class SwingApplicationCommandLineRunner implements CommandLineRunner {
    private BookRepository bookRepository;
    private BookRepository secondaryBookRepository; // Nuevo repositorio

    @Autowired
    public SwingApplicationCommandLineRunner(BookRepository bookRepository, BookRepository secondaryBookRepository) {
        this.bookRepository = bookRepository;
        this.secondaryBookRepository = secondaryBookRepository;
    }

    @Override
    public void run(String... args) {
        // This boots up the GUI.
        EventQueue.invokeLater(() -> new BookUI(bookRepository, secondaryBookRepository).setVisible(true));
    }
}
