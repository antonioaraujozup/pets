package br.com.zup.edu.pets.api.repository;

import br.com.zup.edu.pets.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
