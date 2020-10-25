package mvi.diceapp.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class BasicDiceService {

    private final DiceRunDataService persistence;

    @Autowired
    public BasicDiceService(DiceRunDataService persistence) {
        this.persistence = persistence;
    }

    public Map<Long, Integer> rollOfDice(int rolls, int sides, int dices, final Random rdm) {
        val diceDistrib = new HashMap<Long, Integer>();
        for (int roundOfThrows = 0; roundOfThrows < rolls; roundOfThrows++) {
            Long sum = sumXDices(sides, dices, rdm);
            diceDistrib.merge(sum, 1, Integer::sum);
        }
        persistence.saveResults(rolls, sides, dices, diceDistrib);
        return diceDistrib;
    }

    long sumXDices(final int sides, final int dices, final Random rdm) {
        long sum = 0;
        for (int d = 0; d < dices; d++) {
            sum += 1 + rdm.nextInt(sides);
        }
        return sum;
    }
}
