package com.example.webbinderbug;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
public class UserController {

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public String search(SearchCriteria searchCriteria) {

        return searchCriteria.getName() + "|" + searchCriteria.getPage() + "|" + searchCriteria.getSize();

    }
}
