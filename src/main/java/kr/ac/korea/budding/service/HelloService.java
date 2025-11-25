package kr.ac.korea.budding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {

    public String sayHello() {
        return "hello";
    }
}
