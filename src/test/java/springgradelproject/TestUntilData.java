package springgradelproject;

import springgradelproject.domain.dto.AuthorDto;
import springgradelproject.domain.dto.BookDto;
import springgradelproject.domain.entities.AuthorEntity;
import springgradelproject.domain.entities.BookEntity;

public class TestUntilData {
        public static AuthorDto CreateTestAuthorA(){
            return AuthorDto.builder()
                    .name("Cow boy")
                    .age(34)
                    .build();
        }
        public static AuthorDto CreateTestAuthorB(){
            return AuthorDto.builder()
                    .name("John Doe")
                    .age(57)
                    .build();
        }
        public static AuthorDto CreateTestAuthorC(){
            return AuthorDto.builder()
                    .name("David Benda")
                    .age(87)
                    .build();
        }

        public static BookDto CreateTestBookA(AuthorEntity author){
            return BookDto.builder()
                    .isbn("99-7244-676-10")
                    .title("El Cancer Pez")
                    .author(author)
                    .build();
        }

        public static BookDto CreateTestBookB(AuthorEntity author){
            return BookDto.builder()
                    .isbn("99-7283-041-1")
                    .title("El Glo Motorist")
                    .author(author)
                    .build();
        }

        public static BookDto CreateTestBookC(AuthorEntity author){
            return BookDto.builder()
                    .isbn("99-7221-045-6")
                    .title("El Camino Mec")
                    .author(author)
                    .build();
        }
}
