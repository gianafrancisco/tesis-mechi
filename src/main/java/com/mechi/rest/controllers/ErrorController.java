package com.mechi.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class ErrorController {

    @RequestMapping("/error")
    public String error() {
        return "redirect:/error.html";
    }
}
