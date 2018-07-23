package com.inther.service;

import com.inther.EntityNotFoundException;
import com.inther.entity.Player;
import com.inther.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player getPlayerNoException(String login) throws EntityNotFoundException {
        Player player = playerRepository.findByLogin(login);
        return player;
    }

    public Player getPlayer(String login) throws EntityNotFoundException {
        Player player = playerRepository.findByLogin(login);
        if(player == null){
            throw new EntityNotFoundException(Player.class, "login", login);
        }
        return player;
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }
}