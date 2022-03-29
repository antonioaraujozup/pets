package br.com.zup.edu.pets.api.controller;

import br.com.zup.edu.pets.api.model.Pet;
import br.com.zup.edu.pets.api.model.TipoPet;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class PetRequest {

    @NotBlank
    private String nome;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull
    private TipoPet tipo;

    @NotBlank
    private String raca;

    public Pet paraPet() {
        return new Pet(nome,dataNascimento,tipo,raca);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTipo(TipoPet tipo) {
        this.tipo = tipo;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

}
