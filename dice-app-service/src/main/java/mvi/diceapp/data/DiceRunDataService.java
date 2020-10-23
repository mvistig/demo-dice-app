package mvi.diceapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import mvi.diceapp.request.DiceRun;
import mvi.diceapp.request.DiceRunGroupingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DiceRunDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiceRunDataService.class);
    @Autowired
    private DiceRunRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    public void saveResults(int rolls, int sides, int dices, Map<Long, Integer> results) {
        val entity = new DiceRun();
        entity.setNumberOfDices(dices);
        entity.setNumberOfRolls(rolls);
        entity.setSidesOfDice(sides);
        try {
            entity.setResultMap(objectMapper.writeValueAsString(results));
        } catch (JsonProcessingException e) {
            //Nothing to do, don't stop the processing because of a persistence problem
            LOGGER.warn("Can't serialize the result map");
            return;
        }
        repository.save(entity);
    }

    public List<DiceRunGroupingDto> getAllGroupedBySidesAndDices() {
        return repository.getSumOfRollsGrouped().stream().map(this::toGroupingDto).collect(Collectors.toList());
    }

    /**
     * Helper function, transforms results[] into a dto
     */
    private DiceRunGroupingDto toGroupingDto(Object[] results) {
        val dr = new DiceRunGroupingDto();
        dr.setTotalRolls((long) results[0]);
        dr.setTotalNumberOfSimulations((long) results[1]);
        dr.setBySides((int) results[2]);
        dr.setByDices((int) results[3]);
        return dr;
    }


}
