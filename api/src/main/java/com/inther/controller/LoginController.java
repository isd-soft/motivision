package com.inther.controller;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import com.inther.entity.Player;
import com.inther.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PlayerRepository playerRepository;


    @RequestMapping("/login")
    public Map<String, String> login(@RequestParam(value = "login", defaultValue = "null") String login,
                                     @RequestParam(value = "password", defaultValue = "null") String password) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
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
                    //Put all Strength
                } else {
                    map.put("status", "failed");
                    map.put("message", "wrong password");
                }
            }
        } else {
            map.put("result", "failed");
            map.put("message", "no login and/or password");
        }
        return map;
}

    @RequestMapping("/register_player")
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
                map.put("id", player.getID().toString());
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

    @RequestMapping("/player_exist")
    public Map<String, String> playerExist(@RequestParam(name = "login", defaultValue = "kek") String login) {
        TreeMap<String, String> map = new TreeMap<>();
        if (playerRepository.findByLogin(login) == null) {
            map.put("status", "success");
            map.put("result", "false");
            return map;
        }
        map.put("status", "success");
        map.put("result", "true");
        return map;
    }
}