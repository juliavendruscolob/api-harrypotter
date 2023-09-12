package springbootapi.springbootapiharrypotter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootapi.springbootapiharrypotter.repository.CharacterRepository;
import springbootapi.springbootapiharrypotter.entity.Character;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CharacterImplService implements CharacterService{
    
    @Autowired
    private CharacterRepository characterRepository;

    public CharacterImplService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Character createCharacter(Character character){
        return  characterRepository.save(character);
    }

    @Override
    public Character getCharacterById(UUID characterId){
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        return optionalCharacter.get();
    }

    @Override
    public List<Character> getAllCharacters(){
        return characterRepository.findAll();
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
