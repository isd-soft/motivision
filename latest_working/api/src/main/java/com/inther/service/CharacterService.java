package com.inther.service;

import com.inther.EntityNotFoundException;
import com.inther.entity.Character;
import com.inther.repo.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public Character getCharacterNoException(Long characterId) throws EntityNotFoundException {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        Character character = optionalCharacter.get();
        return character;
    }

    public Character getCharacter(Long characterId) throws EntityNotFoundException {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);
        Character character = optionalCharacter.get();
        if(character == null){
            throw new EntityNotFoundException(Character.class, "id", characterId.toString());
        }
        return character;
    }

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }
}