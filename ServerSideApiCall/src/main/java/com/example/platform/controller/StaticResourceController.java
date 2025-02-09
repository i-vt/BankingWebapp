package com.example.platform.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class StaticResourceController {

    @GetMapping("/css/{filename:.+}")
    @ResponseBody
    public Resource getCss(@PathVariable String filename) throws IOException {
        return new ClassPathResource("static/css/" + filename);
    }

    @GetMapping("/js/{filename:.+}")
    @ResponseBody
    public Resource getJs(@PathVariable String filename) throws IOException {
        return new ClassPathResource("static/js/" + filename);
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public Resource getImage(@PathVariable String filename) throws IOException {
        return new ClassPathResource("static/images/" + filename);
    }
}
