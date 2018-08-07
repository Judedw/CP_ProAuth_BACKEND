package com.product.genuine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WelcomeController
 * Created by nuwan on 7/22/18.
 */
@RestController
public class WelcomeController {

    @GetMapping(value = "/welcome")
    public String welcome() {

        return "Welcome to GSP";

    }
}
