package springbootapi.springbootapiharrypotter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapi.springbootapiharrypotter.ElkLogger.ElkLoggerService;
import springbootapi.springbootapiharrypotter.repository.CharacterRepository;
import springbootapi.springbootapiharrypotter.entity.Character;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class Payload {
    public String endpoint;
    public String method;
    public Object data;

    public Payload(String endpoint, String method, Object data) {
        this.endpoint = endpoint;
        this.method = method;
        this.data = data;
    }
}

@Service
public class CharacterImplService implements CharacterService{

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    public ElkLoggerService elkLoggerService;

    public CharacterImplService(CharacterRepository characterRepository, ElkLoggerService elkLoggerService) {
        this.characterRepository = characterRepository;
        this.elkLoggerService = elkLoggerService;
    }

    @Override
    public Character createCharacter(Character character) throws JsonProcessingException {
        Character createdCharacter = characterRepository.save(character);
        Payload payload = new Payload("/character", "POST", createdCharacter);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);
        elkLoggerService.ElkLogger(payload).subscribe();
        return createdCharacter;
    }

    @Override
    public Character getCharacterById(UUID characterId){
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        elkLoggerService.ElkLogger(optionalCharacter).subscribe();
        return optionalCharacter.get();
    }

    @Override
    public List<Character> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        elkLoggerService.ElkLogger(characters).subscribe();
        return characters;
    }

    @Override
    public Character updateCharacter(Character character){
        Character existingCharacter = characterRepository.findById(character.getId()).get();
        existingCharacter.setName(character.getName());
        existingCharacter.setHouse(character.getHouse());
        existingCharacter.setPatron(character.getPatron());
        Character updatedCharacter = characterRepository.save(existingCharacter);
        return updatedCharacter;
    }

    @Override
    public void deleteCharacter(UUID characterId){
        characterRepository.deleteById(characterId);
    }
}
