package jpaswing.repository;

import jpaswing.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findFirstByOrderByIdAsc();
    
    @Query("SELECT COUNT(b) FROM Book b")
    int countAllRecords();
}
