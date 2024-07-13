package springgradelproject.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springgradelproject.TestUntilData;
import springgradelproject.domain.dto.AuthorDto;
import springgradelproject.domain.dto.BookDto;
import springgradelproject.domain.entities.AuthorEntity;
import springgradelproject.mappers.Mapper;
import springgradelproject.services.BookService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final Mapper<AuthorEntity,AuthorDto> authorMapper;
    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, Mapper<AuthorEntity,AuthorDto> authorMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorMapper = authorMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatBookCanBeCreatedReturns201Created() throws Exception {
        AuthorDto testAuthorDto = TestUntilData.CreateTestAuthorA();
        BookDto testBookDto = TestUntilData.CreateTestBookA(authorMapper.mapTo(testAuthorDto));
        String bookJson = objectMapper.writeValueAsString(testBookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatBookCanBeCreatedReturnsCreatedBookJson() throws Exception {
        BookDto testBookDto = TestUntilData.CreateTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(testBookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBookDto.getAuthor())
        );
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalculatedStatusHttp200() throws Exception {
        BookDto testBookDto = TestUntilData.CreateTestBookA(authorMapper.mapTo(TestUntilData.CreateTestAuthorA()));
        BookDto savedBookDto = bookService.createBook(testBookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+ savedBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(savedBookDto.getAuthor())
        );
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalculatedStatusHttp200() throws Exception {
        BookDto testBookDtoA = TestUntilData.CreateTestBookA(authorMapper.mapTo(TestUntilData.CreateTestAuthorA()));
        BookDto testBookDtoB = TestUntilData.CreateTestBookB(authorMapper.mapTo(TestUntilData.CreateTestAuthorB()));
        BookDto savedBookA = bookService.createBook(testBookDtoA);
        BookDto savedBookB = bookService.createBook(testBookDtoB);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(savedBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(savedBookA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").value(savedBookA.getAuthor())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].isbn").value(savedBookB.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].title").value(savedBookB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].author").value(savedBookB.getAuthor())
        );
    }

    @Test
    public void testThatBookCanBeUpdatedAndRecalculatedStatusHttp200() throws Exception {
        BookDto testBookDtoA = TestUntilData.CreateTestBookA(authorMapper.mapTo(TestUntilData.CreateTestAuthorA()));
        BookDto savedBookA = bookService.createBook(testBookDtoA);
        savedBookA.setTitle("New Title");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ savedBookA.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedBookA))
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedBookA.getTitle())
        );
    }

    @Test
    public void testThatBookCanBePatchedAndRecalculatedStatusHttp200() throws Exception {
        BookDto testBookDtoA = TestUntilData.CreateTestBookA(authorMapper.mapTo(TestUntilData.CreateTestAuthorA()));
        BookDto savedBookA = bookService.createBook(testBookDtoA);
        savedBookA.setTitle("New Title");
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+ savedBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedBookA))
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedBookA.getTitle())
        );
    }

    @Test
    public void testThatBookCanBeDeletedAndReturnsStatusHttp204NoContent() throws Exception {
        BookDto testBookDtoA = TestUntilData.CreateTestBookA(authorMapper.mapTo(TestUntilData.CreateTestAuthorA()));
        BookDto savedBookA = bookService.createBook(testBookDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/"+ savedBookA.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
