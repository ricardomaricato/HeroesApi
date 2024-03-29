package com.digitalinnovation.heroesapi.controller;

import com.digitalinnovation.heroesapi.document.Heroes;
import com.digitalinnovation.heroesapi.service.HeroesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.digitalinnovation.heroesapi.constans.HeroesConstant.HEROES_ENDPOINT_LOCAL;


@Slf4j
@RestController
@RequiredArgsConstructor
public class HeroesController {

    private final HeroesService heroesService;

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> listAll() {
        log.info("Requesting the list off all heroes");
        return heroesService.findAll();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL + "/id")
    public Mono<ResponseEntity<Heroes>> findById(@PathVariable String id) {
        log.info("Requesting the hero with id {}", id);
        return heroesService.findById(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        log.info("A new was created");
        return heroesService.save(heroes);
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CONTINUE)
    public Mono<HttpStatus> delete(@PathVariable String id) {
        heroesService.delete(id);
        log.info("Deleting a hero with id {}", id);
        return Mono.just(HttpStatus.CONTINUE);
    }
}
