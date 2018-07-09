package com.inther.controller;

import com.inther.aspect.Logging;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ExitController {
    private Logger log = Logger.getLogger(ExitController.class);

    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public void exit() {
        log.info("STOPPING THE SERVER");
        System.exit(0);
    }
}
