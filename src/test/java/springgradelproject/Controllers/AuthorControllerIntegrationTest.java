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
import springgradelproject.services.AuthorService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    public final AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTest(ObjectMapper objectMapper,MockMvc mockMvc, AuthorService authorService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.authorService = authorService;
    }

    @Test
    public void testTHatAuthorCanBeCreatedReturnsHttp201Created() throws Exception {
        AuthorDto testAuthorDto = TestUntilData.CreateTestAuthorA();
        String testAuthorJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)

        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalledStatusHttp200() throws Exception {
        AuthorDto testAuthorDto = authorService.createAuthor(TestUntilData.CreateTestAuthorA());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthorDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge())
        );

    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndRecalledStatusHttp200() throws Exception {
        AuthorDto testAuthorDtoA = authorService.createAuthor(TestUntilData.CreateTestAuthorA());
        AuthorDto testAuthorDtoB = authorService.createAuthor(TestUntilData.CreateTestAuthorB());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(testAuthorDtoA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").value(testAuthorDtoB.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testAuthorDtoA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(testAuthorDtoB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(testAuthorDtoA.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(testAuthorDtoB.getAge())
        );
    }

    @Test
    public void testTHatAuthorCanNotBeUpdatedReturns404NotFound() throws Exception {
        AuthorDto testAuthorDto = TestUntilData.CreateTestAuthorA();
        String testAuthorJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatAuthorCanBeUpdatedAndRecalledStatusHttp200() throws Exception {
        AuthorDto testAuthorDto = authorService.createAuthor(TestUntilData.CreateTestAuthorA());
        testAuthorDto.setName("testAuthor");
        String testAuthorJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+testAuthorDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName())
        );
    }

    @Test
    public void testThatAuthorCanBePatchedAndRecalledStatusHttp200() throws Exception {
        AuthorDto testAuthorDto = authorService.createAuthor(TestUntilData.CreateTestAuthorA());
        testAuthorDto.setName("testAuthor");
        String testAuthorJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName())
        );
    }

    @Test
    public void testThatAuthorCanBeDeletedAndReturnsStatus204NoContent() throws Exception {
        AuthorDto testAuthorDto = authorService.createAuthor(TestUntilData.CreateTestAuthorA());
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/"+testAuthorDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
