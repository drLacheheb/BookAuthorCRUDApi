package springgradelproject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springgradelproject.domain.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    BookDto readBookByIsbn(String isbn);
    List<BookDto> readAllBooks();
    Page<BookDto> readAllBooks(Pageable pageable);
    BookDto updateBook(BookDto bookDto);
    BookDto patchBook(BookDto bookDto);
    Boolean isExists(String isbn);
    void deleteBook(String isbn);
}
