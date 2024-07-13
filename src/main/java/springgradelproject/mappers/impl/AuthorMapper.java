package springgradelproject.mappers.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import springgradelproject.domain.dto.AuthorDto;
import springgradelproject.domain.entities.AuthorEntity;
import springgradelproject.mappers.Mapper;

@Component
@AllArgsConstructor
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {
    private final ModelMapper modelMapper;

    @Override
    public AuthorEntity mapTo(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }

    @Override
    public AuthorDto mapFrom(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }
}
