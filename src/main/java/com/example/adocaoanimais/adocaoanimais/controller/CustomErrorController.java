package com.example.adocaoanimais.adocaoanimais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    @GetMapping("/error")
    public String handleError() {
        return "error"; // Retorne a página de erro que você criou
    }
}
