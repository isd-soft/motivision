package com.inther.controller;


import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import com.inther.entity.Character;
import com.inther.entity.Player;
import com.inther.repo.CharacterRepository;
import com.inther.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CharacterRepository characterRepository;

    /*
     * Login controller
     * @param login - user login
     * @param password - user password
     * @return Json data
     * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam(value = "login", defaultValue = "null") String login,
                                     @RequestParam(value = "password", defaultValue = "null") String password) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (!(login.equals("null")) && !(password.equals("null"))) {
            Player player = playerRepository.findByLogin(login);
            if (player == null) {
                map.put("status", "failed");
                map.put("message", "no such player exist");
            } else {
                if (player.getPassword().equals(password)) {
                    map.put("status", "success");
                    map.put("id", player.getID().toString());
                    map.put("points", player.getPoints().toString());
                    ArrayList<Character> characters = characterRepository.findByPlayerID(player.getID());
                    Iterator<Character> iterator = characters.iterator();
                    ArrayList<Map<String, Object>> result = new ArrayList<>();
                    while (iterator.hasNext()) {
                        Map<String, Object> characterMap = new TreeMap<>();
                        Character character = iterator.next();
                        characterMap.put("id", character.getID().toString());
                        characterMap.put("nickname", character.getName());
                        result.add(characterMap);
                    }
                    map.put("characters", result);

                } else {
                    map.put("status", "failed");
                    map.put("message", "wrong password");
                }
            }
        } else {
            map.put("status", "failed");
            map.put("message", "no login and/or password");
        }
        return map;
    }

    /*
     * Register player controller
     * @param login - user login to register
     * @param password - user password to register
     * @return Json representation of result logs
     * */
    @RequestMapping(value = "/register_player", method = RequestMethod.POST)
    public Map<String, String> newPlayer(@RequestParam(value = "login", defaultValue = "null") String login,
                                         @RequestParam(value = "password", defaultValue = "null") String password) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Player player = new Player();
        player.setLogin(login);
        player.setPassword(password);
        if (!(login.equals("null")) && !(password.equals("null"))) {
            try {
                playerRepository.save(player);
                map.put("status", "success");
                playerRepository.findByLogin(login);
                map.put("characterId", player.getID().toString());
                map.put("points", player.getPoints().toString());
                return map;
            } catch (Exception e) {
                map.put("status", "failed");
                map.put("message", "exception occurred");
                return map;
            }
        } else {
            map.put("status", "failed");
            map.put("message", "no login and/or password");
            return map;
        }
    }

    /*
     * Check if player exist controller
     * @param login - login to check in the database
     * @param Json representation of the result logs
     * */
    @RequestMapping(value = "/player_exist", method = RequestMethod.POST)
    public Map<String, String> playerExist(@RequestParam(name = "login") String login) {
        TreeMap<String, String> map = new TreeMap<>();
        if (playerRepository.findByLogin(login) == null) {
            map.put("status", "success");
            map.put("message", "false");
            return map;
        }
        map.put("status", "success");
        map.put("message", "true");
        return map;
    }
}