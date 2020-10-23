package mvi.diceapp.rest;

import mvi.diceapp.request.DiceRunGroupingDto;
import mvi.diceapp.service.DiceRunDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
public class DiceRunResource {
    @Autowired
    private DiceRunDataService diceRunDataService;

    @GetMapping("/dicerun/total-grouped")
    @ResponseStatus(HttpStatus.OK)
    public List<DiceRunGroupingDto> getTotalGrouped() {
        return diceRunDataService.getAllGroupedBySidesAndDices();
    }

    @GetMapping("/dicerun/relative-distribution")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> getRelativeDistributionOfSums(@NotNull int numberOfDices, @NotNull int numberOfSides) {
        return diceRunDataService.getRelativeDistributionOfSums(numberOfDices, numberOfSides);
    }
}
