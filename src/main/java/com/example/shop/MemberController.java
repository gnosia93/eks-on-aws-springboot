package com.example.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/member", method= RequestMethod.GET)
    public String member() {

        logger.trace("trace...");
        logger.debug("debug...");
        logger.info("info...");
        logger.warn("warn ...");
        logger.error("error ...");

        return "dummy member";
    }
}
