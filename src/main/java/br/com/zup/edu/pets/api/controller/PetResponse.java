package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.model.Pet;
import br.com.zup.edu.pets.api.model.TipoPet;

public class PetResponse {

    private String nome;
    private String raca;
    private TipoPet tipo;

    public PetResponse(Pet pet) {
        this.nome = pet.getNome();
        this.raca = pet.getRaca();
        this.tipo = pet.getTipo();
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public TipoPet getTipo() {
        return tipo;
    }
}
