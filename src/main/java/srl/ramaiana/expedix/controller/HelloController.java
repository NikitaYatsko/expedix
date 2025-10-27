package srl.ramaiana.expedix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class HelloController {
    @GetMapping
    public String greeting() {
        return "working";
    }
}
