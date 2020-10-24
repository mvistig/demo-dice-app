package mvi.diceapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import mvi.diceapp.data.DiceRunRepository;
import mvi.diceapp.request.DiceRun;
import mvi.diceapp.request.DiceRunGroupingDto;
import mvi.diceapp.util.DiceServiceRtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DiceRunDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiceRunDataService.class);

    private final DiceRunRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public DiceRunDataService(DiceRunRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

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


    public Map<Long, Double> getRelativeDistributionOfSums(int numberOfDices, int numberOfSides) {
        var diceRuns = repository.getAllByNumberOfDicesAndSidesOfDice(numberOfDices, numberOfSides);
        var totalHitsMap = new HashMap<Long, Long>();
        var totalRolls = 0L;
        for (DiceRun dr : diceRuns) {
            Map<Long, Integer> hitMap;
            try {
                hitMap = objectMapper.readValue(dr.getResultMap(), new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                LOGGER.warn("Exception thrown when reading id {}", dr.getId(), e);
                throw new DiceServiceRtException(e.getMessage(), "DB JSON Deserializing");
            }
            hitMap.forEach((sum, hits) -> totalHitsMap.merge(sum, hits.longValue(), Long::sum));
            totalRolls += dr.getNumberOfRolls();
        }
        //now we have the total hit map, we can create the relative distribution map
        Map<Long, Double> relativeDistrib = new HashMap<>();
        final long finalTotalRolls = totalRolls;
        totalHitsMap.forEach((sum, hits) -> relativeDistrib.put(sum, hits / (double) finalTotalRolls));
        return relativeDistrib;
    }
}
