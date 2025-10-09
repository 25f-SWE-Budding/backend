package kr.ac.korea.sweteam6.controller;

import kr.ac.korea.sweteam6.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HelloController {
    private final HelloService helloService;

    @GetMapping("/")
    public String sayHello() {
        return helloService.sayHello();
    }
}
