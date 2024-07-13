package springgradelproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springgradelproject.domain.dto.BookDto;
import springgradelproject.services.BookService;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> creatUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        if (bookService.isExists(isbn)){
            bookDto.setIsbn(isbn);
            return new ResponseEntity<>(bookService.updateBook(bookDto), HttpStatus.OK);
        }else {
            bookDto.setIsbn(isbn);
            return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
        }
    }

    @GetMapping("")
    public Page<BookDto> readAllBooks(Pageable pageable) {
        return bookService.readAllBooks(pageable);
    }

    @GetMapping("/{isbn}")
    public BookDto readBookByIsbn(@PathVariable("isbn") String isbn) {
        return bookService.readBookByIsbn(isbn);
    }

    @PatchMapping("/{isbn}")
    public ResponseEntity<BookDto> patchBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookDto.setIsbn(isbn);
        return new ResponseEntity<>(bookService.patchBook(bookDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
