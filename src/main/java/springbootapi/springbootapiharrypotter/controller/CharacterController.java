package springbootapi.springbootapiharrypotter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapi.springbootapiharrypotter.entity.Character;
import springbootapi.springbootapiharrypotter.service.CharacterService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping
    public ResponseEntity<Character> createCharacter(@RequestBody Character character) throws JsonProcessingException {
        Character savedCharacter = characterService.createCharacter(character);
        return new ResponseEntity<>(savedCharacter, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable("id")UUID characterId){
        Character character = characterService.getCharacterById(characterId);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters(){
        List<Character> characters = characterService.getAllCharacters();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable("id") UUID characterId, @RequestBody Character character){
        character.setId(characterId);
        Character updatedCharacter = characterService.updateCharacter(character);
        return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable("id") UUID characterId){
        characterService.deleteCharacter(characterId);
        return new ResponseEntity<>("Character successfully deleted.", HttpStatus.OK);
    }
}
