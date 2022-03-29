package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.model.Pet;
import br.com.zup.edu.pets.api.repository.PetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pets")
public class CadastrarNovoPetController {

    private final PetRepository repository;

    public CadastrarNovoPetController(PetRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid PetRequest request, UriComponentsBuilder uriComponentsBuilder) {

        Pet novoPet = request.paraPet();

        repository.save(novoPet);

        URI location = uriComponentsBuilder
                .path("/pets/{id}")
                .buildAndExpand(novoPet.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}
