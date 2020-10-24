package mvi.diceapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mvi.diceapp.request.DiceRollRequest;
import mvi.diceapp.service.BasicDiceService;
import mvi.diceapp.service.DiceRunDataService;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DiceResourceTest {

    @MockBean
    private BasicDiceService service;
    @MockBean
    private DiceRunDataService dataService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @SneakyThrows
    @Test
    @DisplayName("Test get will respond with 200 status and a result")
    void testGetBasicDiceRollWith200() {
        Mockito.when(service.rollOfDice(anyInt(), anyInt(), anyInt(), any())).thenReturn(Map.of(1L, 1));
        mockMvc.perform(MockMvcRequestBuilders.get("/dice").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.distribution", IsNull.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.distribution[0].sum", IsEqual.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.distribution[0].hits", IsEqual.equalTo(1)));
    }

    @SneakyThrows
    @Test
    @DisplayName("Test POST method for dice sim will respond with a BAD_REQUEST if input not valid: dice nb")
    void testPostDiceNumberTooSmall() {
        var req = new DiceRollRequest(200, 0, 6);
        mockMvc.perform(MockMvcRequestBuilders.post("/dice")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test POST method for dice sim will respond with a BAD_REQUEST if input not valid: dice sides")
    void testPostDiceSidesTooSmall() {
        var req = new DiceRollRequest(200, 1, 3);
        mockMvc.perform(MockMvcRequestBuilders.post("/dice")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test POST method for dice sim will respond with a BAD_REQUEST if input not valid: number of rolls")
    void testPostNbRollsTooSmall() {
        var req = new DiceRollRequest(0, 10, 4);
        mockMvc.perform(MockMvcRequestBuilders.post("/dice")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test POST method for dice sim will respond with a OK if input valid")
    void testPostMethod() {
        Mockito.when(service.rollOfDice(anyInt(), anyInt(), anyInt(), any())).thenReturn(Map.of(1L, 1));
        var req = new DiceRollRequest(100, 10, 4);
        mockMvc.perform(MockMvcRequestBuilders.post("/dice")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.distribution", IsNull.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.distribution[0].sum", IsEqual.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.distribution[0].hits", IsEqual.equalTo(1)));
    }
}