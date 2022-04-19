package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.repository.PetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ListarPetsController {

    private final PetRepository repository;

    public ListarPetsController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pets")
    public ResponseEntity<?> listar() {
        List<PetResponse> pets = repository.findAll().stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pets);
    }
}
