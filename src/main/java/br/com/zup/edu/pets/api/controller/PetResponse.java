package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.model.Pet;
import br.com.zup.edu.pets.api.model.TipoPet;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class PetResponse {

    private String nome;
    private TipoPet tipo;
    private String raca;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    public PetResponse(Pet pet) {
        this.nome = pet.getNome();
        this.tipo = pet.getTipo();
        this.raca = pet.getRaca();
        this.dataNascimento = pet.getDataNascimento();
    }

    public String getNome() {
        return nome;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public String getRaca() {
        return raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
