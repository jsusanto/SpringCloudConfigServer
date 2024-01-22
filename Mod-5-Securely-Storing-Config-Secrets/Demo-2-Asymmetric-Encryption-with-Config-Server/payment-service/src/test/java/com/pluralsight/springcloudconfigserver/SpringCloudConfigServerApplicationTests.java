package com.pluralsight.springcloudconfigserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringCloudConfigServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should be able to make a payment")
    void shouldBeAbleToMakeAPayment() throws Exception {

        final UUID bookingId = UUID.fromString("7f35915d-9c72-42c7-a8a5-47eefbef527e");

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"bookingId": "%s",
                                	"paymentAmount" : 100
                                }
                                """.formatted(bookingId)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "total": 117.25
                }
                """));
    }

}
