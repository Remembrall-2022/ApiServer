package com.stella.rememberall.dbCheck;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestEntityController {

    private final TestEntityRepository testEntityRepository;

    @PostMapping("/test/entity")
    public void create() {
        testEntityRepository.save(new TestEntity("new"));
    }

    @GetMapping("/test/{id}")
    public TestEntity get(@PathVariable Long id) {
        return testEntityRepository.findById(id).orElse(new TestEntity("default"));
    }

}
