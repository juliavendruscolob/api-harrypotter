package springbootapi.springbootapiharrypotter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootapi.springbootapiharrypotter.ElkLogger.ElkLoggerService;
import springbootapi.springbootapiharrypotter.repository.CharacterRepository;
import springbootapi.springbootapiharrypotter.entity.Character;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Character createCharacter(Character character) {
        Character createdCharacter = characterRepository.save(character);
        elkLoggerService.ElkLogger(createdCharacter).subscribe();
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
