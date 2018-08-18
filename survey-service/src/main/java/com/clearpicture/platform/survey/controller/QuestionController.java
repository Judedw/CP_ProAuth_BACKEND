package com.clearpicture.platform.survey.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * QuestionController
 * Created by nuwan on 8/17/18.
 */
@Slf4j
@RestController
public class QuestionController {

    /*@PostMapping("${app.endpoint.questionsCreate}")
    public ResponseEntity<SimpleResponseWrapper<QuestionCreateResponse>> create(@Validated @RequestBody QuestionCreateRequest request) {




        return new ResponseEntity<SimpleResponseWrapper<QuestionCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }*/

    @GetMapping("${app.endpoint.questionsCreate}")
    public String create(@PathVariable("name")String name) {


        return name;

        //return new ResponseEntity<SimpleResponseWrapper<QuestionCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }


}
