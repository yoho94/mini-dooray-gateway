package com.nhn.minidooray.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/error")
public class ErrorController {

    @GetMapping
    public String getError() {
        return "error/error";
    }

    @PostMapping
    public String postError() {
        return "error/error";
    }

    @GetMapping("/403")
    public String getError403() {
        return "error/403";
    }

    @PostMapping("/403")
    public String postError403() {
        return "error/403";
    }
}
