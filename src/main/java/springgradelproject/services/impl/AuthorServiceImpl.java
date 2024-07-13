package springgradelproject.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springgradelproject.domain.dto.AuthorDto;
import springgradelproject.domain.entities.AuthorEntity;
import springgradelproject.mappers.Mapper;
import springgradelproject.repositories.AuthorRepository;
import springgradelproject.services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        AuthorEntity savedAuthorEntity = authorRepository.save(authorMapper.mapTo(authorDto));
        return authorMapper.mapFrom(savedAuthorEntity);
    }

    @Override
    public AuthorDto readAuthorById(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElse(null);
        return authorMapper.mapFrom(authorEntity);
    }

    @Override
    public List<AuthorDto> readAllAuthor() {
        List<AuthorEntity> authorEntities = StreamSupport.stream(authorRepository
                .findAll()
                .spliterator(),
                false)
                .toList();
        return authorEntities.stream().map(authorMapper::mapFrom).collect(Collectors.toList());
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        AuthorEntity savedAuthorEntity = authorRepository.save(authorMapper.mapTo(authorDto));
        return authorMapper.mapFrom(savedAuthorEntity);
    }

    @Override
    public AuthorDto patchAuthor(AuthorDto authorDto) {
        Optional<AuthorEntity> savedAuthorEntity = authorRepository.findById(authorDto.getId()).map(existedAuthor ->{
            Optional.ofNullable(authorDto.getName()).ifPresent(existedAuthor::setName);
            Optional.ofNullable(authorDto.getAge()).ifPresent(existedAuthor::setAge);
            return authorRepository.save(existedAuthor);
        });
        return authorMapper.mapFrom(savedAuthorEntity.orElse(null));
    }

    @Override
    public Boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
