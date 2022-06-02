package br.com.zup.edu.petmanager.controller;

import br.com.zup.edu.petmanager.controller.response.PetResponse;
import br.com.zup.edu.petmanager.model.Pet;
import br.com.zup.edu.petmanager.model.TipoPet;
import br.com.zup.edu.petmanager.repository.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class DetalharPetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        this.petRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve detalhar um pet cadastrado")
    void deveDetalharUmPetCadastrado() throws Exception {

        // Cenário
        Pet pet = new Pet("Rodofo", "SRD", TipoPet.CAO, LocalDate.of(2020, Month.DECEMBER, 26));

        petRepository.save(pet);

        MockHttpServletRequestBuilder request = get("/pets/{id}", pet.getId());

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        PetResponse petResponse = mapper.readValue(payloadResponse, PetResponse.class);

        // Asserts
        assertNotNull(petResponse);
        assertThat(petResponse)
                .extracting(
                        PetResponse::getNome,
                        PetResponse::getRaca,
                        PetResponse::getTipo,
                        PetResponse::getIdade
                )
                .contains(
                        pet.getNome(),
                        pet.getRaca(),
                        pet.getTipo().name().toLowerCase(Locale.ROOT),
                        Period.between(pet.getDataNascimento(), LocalDate.now()).getYears()
                );

    }

    @Test
    @DisplayName("Não deve detalhar um pet não cadastrado")
    void naoDeveDetalharUmPetNaoCadastrado() throws Exception {

        // Cenário
        MockHttpServletRequestBuilder request = get("/pets/{id}", Integer.MAX_VALUE);

        // Ação e Corretude
        Exception resolvedException = mockMvc.perform(request)
                .andExpect(
                        status().isNotFound()
                )
                .andReturn()
                .getResolvedException();

        // Asserts
        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        assertEquals("Pet não cadastrado.", ((ResponseStatusException) resolvedException).getReason());

    }

}