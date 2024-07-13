package springgradelproject.services;

import springgradelproject.domain.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto readAuthorById(Long id);
    List<AuthorDto> readAllAuthor();
    AuthorDto updateAuthor(AuthorDto authorDto);
    AuthorDto patchAuthor(AuthorDto authorDto);
    Boolean isExists(Long id);
    void deleteAuthor(Long id);
}
