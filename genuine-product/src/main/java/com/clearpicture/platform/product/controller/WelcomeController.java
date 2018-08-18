package com.clearpicture.platform.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WelcomeController
 * Created by nuwan on 7/12/18.
 * (C) Copyright 2018 nuwan (http://www.efuturesworld.com/) and others.
 */
@RestController
public class WelcomeController {

    @GetMapping("/hello")
    public String hello() {
        return "product service up and running well";
    }
}
