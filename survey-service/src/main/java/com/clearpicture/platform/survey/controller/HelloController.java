package com.clearpicture.platform.survey.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloContrller
 * Created by nuwan on 8/23/18.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Service";
    }

    @GetMapping("/")
    public String helloRoot() {
        return "Hello Root Service";
    }
}
