package com.inther.service;

import com.inther.EntityNotFoundException;
import com.inther.entity.Character;
import com.inther.repo.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public Character getCharacterNoException(Long characterId) throws EntityNotFoundException {
        return characterRepository.findCharacterByID(characterId);
    }

    public Character getCharacter(Long characterId) throws EntityNotFoundException {
        Character character = characterRepository.findCharacterByID(characterId);
        if(character == null){
            throw new EntityNotFoundException(Character.class, "id", characterId.toString());
        }
        return character;
    }

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }
}