package mvi.diceapp.service;

import lombok.val;
import mvi.diceapp.data.DiceRunDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class BasicDiceService {

    @Autowired
    private DiceRunDataService persistence;

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
        Assert.isTrue(dices > 1, "Dice number is too small");
        Assert.isTrue(sides > 4, "Sides number is too small");
        long sum = 0;
        for (int d = 0; d < dices; d++) {
            sum += 1 + rdm.nextInt(sides);
        }
        return sum;
    }
}
