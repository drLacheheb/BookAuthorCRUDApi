package springgradelproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springgradelproject.domain.dto.AuthorDto;
import springgradelproject.services.AuthorService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<AuthorDto> readAllAuthors(){
        return authorService.readAllAuthor();
    }

    @GetMapping("/{id}")
    public AuthorDto readAuthorById(@PathVariable("id") Long id){
        return authorService.readAuthorById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorService.updateAuthor(authorDto), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> patchAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorService.patchAuthor(authorDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
