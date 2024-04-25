package co.edu.udea.sitas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping(produces = "text/html")
    public ResponseEntity<String> sayHola(){
        return ResponseEntity.ok("Hello World");
    }
}
