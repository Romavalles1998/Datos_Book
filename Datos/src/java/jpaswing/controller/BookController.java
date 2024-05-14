package jpaswing.controller;

import jpaswing.entity.Book;
import jpaswing.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookController {
    private final BookRepository bookRepository;
    private int currentPage = 0;
    private int pageSize = 1; // Tamaño de página (número de registros por página)

    @Autowired
    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Optional<Book> next(){
        currentPage++;
        return getCurrentBook();
    }

    public Optional<Book> previous(){
        currentPage--;
        return getCurrentBook();
    }

    public Optional<Book> first(){
        currentPage = 0;
        return getCurrentBook();
    }

    public Optional<Book> last(){
        int totalBooks = bookRepository.countAllRecords();
        currentPage = (int) Math.ceil((double) totalBooks / pageSize) - 1;
        return getCurrentBook();
    }

    private Optional<Book> getCurrentBook() {
        Iterable<Book> booksIterable = bookRepository.findAll();
        List<Book> books = StreamSupport.stream(booksIterable.spliterator(), false)
                .collect(Collectors.toList());

        if (!books.isEmpty()) {
            currentPage = Math.max(0, Math.min(currentPage, books.size() - 1)); // Asegurarse de que currentPage está en el rango válido
            return Optional.of(books.get(currentPage));
        } else {
            return Optional.empty();
        }
    }
}
