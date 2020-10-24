package mvi.diceapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BasicDiceServiceTest {

    @Mock
    private Random random;

    private BasicDiceService service;

    @BeforeEach
    void setUp() {
        service = new BasicDiceService();
    }

    @Test
    @DisplayName("Test that roll of dice returns a map of stats")
    void testRollOfDice() {
        //make it return 0, meaning each roll of dice will be 1
        Mockito.when(random.nextInt(ArgumentMatchers.anyInt())).thenReturn(0);
        var hitMap = service.rollOfDice(3, 6, 3, random);

        assertNotNull(hitMap, "Response must not be null");
        var actualHits = hitMap.get(3);
        assertEquals(3, actualHits, "Number of times the sum was rolled is wrong");
    }

    @Test
    @DisplayName("Test that compute dices returns expected values with 0")
    void testComputeXDicesGivingZero() {
        //make it return 0
        Mockito.when(random.nextInt(ArgumentMatchers.anyInt())).thenReturn(0);
        var diceNb = 3;
        var sum = service.sumXDices(10, diceNb, random);
        assertEquals(diceNb, sum);
        diceNb = 4;
        sum = service.sumXDices(10, diceNb, random);
        assertEquals(diceNb, sum);
        diceNb = 5;
        sum = service.sumXDices(10, diceNb, random);
        assertEquals(diceNb, sum);
    }

    @Test
    @DisplayName("Test that compute dices returns expected values with 1")
    void testComputeXDicesGivingOne() {
        //make it return 1
        Mockito.when(random.nextInt(ArgumentMatchers.anyInt())).thenReturn(1);
        var diceNb = 3;
        var sum = service.sumXDices(10, diceNb, random);
        assertEquals(diceNb * 2, sum);
        diceNb = 4;
        sum = service.sumXDices(10, diceNb, random);
        assertEquals(diceNb * 2, sum);
        diceNb = 5;
        sum = service.sumXDices(10, diceNb, random);
        assertEquals(diceNb * 2, sum);
    }
}