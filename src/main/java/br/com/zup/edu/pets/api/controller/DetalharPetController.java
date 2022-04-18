package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.model.Pet;
import br.com.zup.edu.pets.api.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalharPetController {

    private final PetRepository repository;

    public DetalharPetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> detalhar(@PathVariable("id") Long id) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new PetResponse(pet));
    }
}
