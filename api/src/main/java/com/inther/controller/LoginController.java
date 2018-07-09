package com.inther.controller;


import java.util.*;
import com.inther.aspect.Logging;
import com.inther.entity.Character;
import com.inther.entity.Player;
import com.inther.repo.CharacterRepository;
import com.inther.repo.PlayerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private Logger log = Logger.getLogger(LoginController.class);


    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CharacterRepository characterRepository;

    /*
     * Login controller
     * Used to login to database with a valid username and password
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
                log.warn("No such player with " + login + " exist");
                map.put("status", "failed");
                map.put("message", "Player does not exist");
            } else {
                if (player.getPassword().equals(password)) {
                    log.info("Login is successfull, processing data");
                    map.put("status", "success");
                    map.put("id", player.getID().toString());
                    ArrayList<Character> characters = characterRepository.findByPlayerID(player.getID());
                    Iterator<Character> iterator = characters.iterator();
                    ArrayList<Map<String, Object>> result = new ArrayList<>();
                    log.info("Fetching player characters");
                    while (iterator.hasNext()) {
                        Map<String, Object> characterMap = new TreeMap<>();
                        Character character = iterator.next();
                        characterMap.put("id", character.getID().toString());
                        characterMap.put("nickname", character.getName());
                        result.add(characterMap);
                    }
                    log.info("Characters received");
                    map.put("characters", result);

                } else {
                    log.warn("Wrong password");
                    map.put("status", "failed");
                    map.put("message", "Wrong password");
                }
            }
        } else {
            log.error("No login/password received");
            map.put("status", "failed");
            map.put("message", "No login and/or password provided");
        }
        return map;
    }

    /*
     * Register player controller
     * Used to register a new player in the database
     * with a unique name
     * @param login - user login to register
     * @param password - user password to register
     * @return status - failed if no data was provided
     * @return status - success and playerId if player was registered
     * */
    @RequestMapping(value = "/register_player", method = RequestMethod.POST)
    public Map<String, String> newPlayer(@RequestParam(value = "login", defaultValue = "null") String login,
                                         @RequestParam(value = "password", defaultValue = "null") String password) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Player player = new Player();
        log.info("Player created");
        player.setLogin(login);
        player.setPassword(password);
        if (!(login.equals("null")) && !(password.equals("null"))) {
            try {
                log.info("Login and password are valid");
                playerRepository.save(player);
                log.info("Player saved to the database");
                map.put("status", "success");
                map.put("id", player.getID().toString());
                return map;
            } catch (Exception e) {
                log.error("Exeption occured " + e.getCause());
                map.put("status", "failed");
                map.put("message", "An exception occurred");
                return map;
            }
        } else {
            log.warn("No login or password provided");
            map.put("status", "failed");
            map.put("message", "No login and/or password provided");
            return map;
        }
    }

    /*
     * Check if player exist controller
     * Used to check if a player exist in the database
     * @param login - login to check in the database
     * @return message false if player doesn't exist
     * @return message true if player does exist
     * */
    @RequestMapping(value = "/player_exist", method = RequestMethod.GET)
    public Map<String, String> playerExist(@RequestParam(name = "login") String login) {
        TreeMap<String, String> map = new TreeMap<>();
        if (playerRepository.findByLogin(login) == null) {
            log.info("Player not found");
            map.put("status", "success");
            map.put("message", "false");
            return map;
        }
        log.info("Player found");
        map.put("status", "success");
        map.put("message", "true");
        return map;
    }
}