package me.m41k0n.investment.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.m41k0n.investment.application.service.InvestmentApplicationService;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvestmentController.class)
class InvestmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private InvestmentApplicationService investmentApplicationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldRegisterInvestmentThroughControllerSuccessfully() throws Exception {
        InvestmentRequest request = new InvestmentRequest(
                "Fundo de Renda Fixa",
                "Renda Fixa",
                BigDecimal.valueOf(5000.0),
                LocalDate.now(),
                "Banco do Brasil",
                BigDecimal.valueOf(0.0)
        );
        InvestmentResponse response = new InvestmentResponse(
                "some-id",
                request.name(),
                request.type(),
                request.investmentValue(),
                request.purchaseDate(),
                request.broker(),
                request.purchaseRate()
        );

        when(investmentApplicationService.registerInvestment(any(InvestmentRequest.class))).thenReturn(response);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/investments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        verify(investmentApplicationService, times(1)).registerInvestment(any(InvestmentRequest.class));
    }

    @Test
    void shouldReturnBadRequestWhenRegistrationRequestIsInvalid() throws Exception {
        InvestmentRequest invalidRequest = new InvestmentRequest("", "", BigDecimal.valueOf(-1.0), null, "", BigDecimal.valueOf(-1.0));

        String jsonRequest = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post("/api/investments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

        verify(investmentApplicationService, never()).registerInvestment(any(InvestmentRequest.class));
    }
}