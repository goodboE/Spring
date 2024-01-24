package com.ko.tricount.controller;

import com.ko.tricount.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ExpenseControllerTest {

    @Autowired
    ExpenseService expenseService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEndpoint() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("{}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void 정산_지출내역_확인() throws Exception {
        mockMvc.perform(get("/expense/1"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("{}", result.getResponse().getContentAsString());
                });
    }

}