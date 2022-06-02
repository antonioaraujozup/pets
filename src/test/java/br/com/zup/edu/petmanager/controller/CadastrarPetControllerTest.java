package br.com.zup.edu.petmanager.controller;

import br.com.zup.edu.petmanager.controller.request.PetRequest;
import br.com.zup.edu.petmanager.controller.request.TipoPetRequest;
import br.com.zup.edu.petmanager.model.Pet;
import br.com.zup.edu.petmanager.repository.PetRepository;
import br.com.zup.edu.petmanager.util.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarPetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        this.petRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar um Pet com dados válidos")
    void deveCadastrarUmPetComDadosValidos() throws Exception {

        // Cenário
        PetRequest petRequest = new PetRequest("Rodolfo", "SRD", TipoPetRequest.CAO, LocalDate.of(2020, Month.DECEMBER, 26));

        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request = post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        // Ação e Corretude
        mockMvc.perform(request)
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        redirectedUrlPattern("http://localhost/pets/*")
                );

        // Asserts
        List<Pet> pets = petRepository.findAll();
        assertEquals(1, pets.size());

    }

    @Test
    @DisplayName("Não deve cadastrar um Pet com dados nulos")
    void naoDeveCadastrarUmPetComDadosNulos() throws Exception {

        // Cenário
        PetRequest petRequest = new PetRequest(null,null,null,null);

        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request = post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(4, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo nome não deve estar em branco",
                "O campo dataNascimento não deve ser nulo",
                "O campo raca não deve estar em branco",
                "O campo tipo não deve ser nulo"
        ));

    }

    @Test
    @DisplayName("Não deve cadastrar um Pet com data de nascimento inválida")
    void naoDeveCadastrarUmPetComDataDeNascimentoInvalida() throws Exception {

        // Cenário
        PetRequest petRequest = new PetRequest("Rodolfo", "SRD", TipoPetRequest.CAO, LocalDate.of(2022, Month.DECEMBER, 26));

        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request = post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo dataNascimento deve ser uma data passada"
        ));

    }
}