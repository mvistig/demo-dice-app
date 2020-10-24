package mvi.diceapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mvi.diceapp.data.DiceRunRepository;
import mvi.diceapp.request.DiceRun;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiceRunDataServiceTest {

    @Mock
    private DiceRunRepository repository;
    @Mock
    private ObjectMapper objectMapper;

    //CUT
    private DiceRunDataService service;

    @BeforeEach
    void setUp() {
        service = new DiceRunDataService(repository, objectMapper);
    }

    @SneakyThrows
    @Test
    @DisplayName("Save results should create a correct entity, create a json out of the hitMap, and save it all in the repository")
    void testSaveResults() {
        final ArgumentCaptor<DiceRun> entityCaptor = ArgumentCaptor.forClass(DiceRun.class);
        when(objectMapper.writeValueAsString(any())).thenReturn("TEST");

        service.saveResults(10, 6, 3, Map.of(1L, 1, 2L, 2, 3L, 3));
        verify(repository).save(entityCaptor.capture());

        final var actualEntity = entityCaptor.getValue();

        assertNotNull(actualEntity, "repository.save was not called");
        assertEquals("TEST", actualEntity.getResultMap());
        assertEquals(10, actualEntity.getNumberOfRolls());
        assertEquals(6, actualEntity.getSidesOfDice());
        assertEquals(3, actualEntity.getNumberOfDices());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test that get all grouped parses correctly the results from repository which are a list of Object[]")
    void testGetAllGroupedBySidesAndDices() {
        Object[] a = {4000L, 44L, 4, 4};
        Object[] b = {2000L, 22L, 2, 2};
        when(repository.getSumOfRollsGrouped()).thenReturn(List.of(a, b));

        var actualList = service.getAllGroupedBySidesAndDices();
        assertNotNull(actualList, "Result of service was null");
        assertEquals(2, actualList.size(), "List of 2 was expected");
        assertTrue(actualList.stream().anyMatch(dto -> dto.getByDices() == 2));
        assertTrue(actualList.stream().anyMatch(dto -> dto.getByDices() == 4));
    }

    @SneakyThrows
    @Test
    void testGetRelativeDistributionOfSums() {
        when(repository.getAllByNumberOfDicesAndSidesOfDice(4, 4)).thenReturn(List.of(composeDiceRun(4), composeDiceRun(4)));
        doReturn(Map.of(1L, 200, 2L, 200),
                 Map.of(2L, 200, 3L, 200)).when(objectMapper).readValue(anyString(), any(TypeReference.class));
        var actual = service.getRelativeDistributionOfSums(4, 4);
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals(0.25, actual.get(1L));
        assertEquals(0.5, actual.get(2L));
        assertEquals(0.25, actual.get(3L));
    }

    private DiceRun composeDiceRun(int seed) {
        var d = new DiceRun();
        d.setId((long) seed);
        d.setNumberOfDices(seed);
        d.setSidesOfDice(seed);
        d.setNumberOfRolls(seed * 100);
        d.setResultMap("TEST");
        return d;
    }
}