package mvi.diceapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mvi.diceapp.request.DiceRunGroupingDto;
import mvi.diceapp.service.BasicDiceService;
import mvi.diceapp.service.DiceRunDataService;
import org.hamcrest.core.IsEqual;
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

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DiceRunResourceTest {

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
    @DisplayName("Test that the resource responds with OK status and a valid response")
    void testGetTotalGrouped() {
        Mockito.when(dataService.getAllGroupedBySidesAndDices()).thenReturn(List.of(new DiceRunGroupingDto(1, 1, 1, 1)));
        mockMvc.perform(MockMvcRequestBuilders.get("/dicerun/total-grouped").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalNumberOfSimulations", IsEqual.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].byDices", IsEqual.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bySides", IsEqual.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalRolls", IsEqual.equalTo(1)));
    }

    @SneakyThrows
    @Test
    @DisplayName("Test that the relative distribution responds with a valid answer and a 200 status")
    void getRelativeDistributionOfSums() {
        Mockito.when(dataService.getRelativeDistributionOfSums(anyInt(), anyInt())).thenReturn(Map.of(1L, 0.1, 2L, 0.3));
        mockMvc.perform(MockMvcRequestBuilders.get("/dicerun/relative-distribution?numberOfDices=2&numberOfSides=0")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.1", IsEqual.equalTo(0.1)))
        ;

    }
}