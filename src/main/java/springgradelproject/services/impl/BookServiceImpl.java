package springgradelproject.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springgradelproject.domain.dto.BookDto;
import springgradelproject.domain.entities.BookEntity;
import springgradelproject.mappers.Mapper;
import springgradelproject.repositories.BookRepository;
import springgradelproject.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final Mapper<BookEntity, BookDto> bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapTo(bookDto);
        return bookMapper.mapFrom(bookRepository.save(bookEntity));
    }

    @Override
    public BookDto readBookByIsbn(String isbn) {
        BookEntity bookEntity = bookRepository.findById(isbn).orElse(null);
        return bookMapper.mapFrom(bookEntity);
    }

    @Override
    public List<BookDto> readAllBooks() {
        List<BookEntity> bookEntities = StreamSupport.stream(bookRepository
                .findAll()
                .spliterator(),
                false)
                .toList();
        return bookEntities.stream().map(bookMapper::mapFrom).collect(Collectors.toList());
    }

    @Override
    public Page<BookDto> readAllBooks(Pageable pageable) {
        Page<BookEntity> books = bookRepository.findAll(pageable);
        return books.map(bookMapper::mapFrom);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapTo(bookDto);
        return bookMapper.mapFrom(bookRepository.save(bookEntity));
    }

    @Override
    public BookDto patchBook(BookDto bookDto) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookDto.getIsbn()).map(existedBook->{
            Optional.ofNullable(bookDto.getTitle()).ifPresent(existedBook::setTitle);
            Optional.ofNullable(bookDto.getAuthor()).ifPresent(existedBook::setAuthor);
            return bookRepository.save(existedBook);
        });
        return bookMapper.mapFrom(bookEntity.orElse(null));
    }

    @Override
    public Boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
