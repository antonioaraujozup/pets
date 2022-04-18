package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.model.Pet;
import br.com.zup.edu.pets.api.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class RemoverPetController {

    private final PetRepository repository;

    public RemoverPetController(PetRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @DeleteMapping("/pets/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado");
                });

        repository.delete(pet);

        return ResponseEntity.noContent().build();
    }
}
