package springbootapi.springbootapiharrypotter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import springbootapi.springbootapiharrypotter.entity.Character;

import java.util.List;
import java.util.UUID;

public interface CharacterService {
    Character createCharacter(Character character);

    Character getCharacterById(UUID characterId);

    List<Character> getAllCharacters();

    Character updateCharacter(Character character);

    void deleteCharacter(UUID characterId);
}
