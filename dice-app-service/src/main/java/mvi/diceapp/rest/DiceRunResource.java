package mvi.diceapp.rest;

import mvi.diceapp.request.DiceRunGroupingDto;
import mvi.diceapp.service.DiceRunDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
public class DiceRunResource {

    private final DiceRunDataService diceRunDataService;

    @Autowired
    public DiceRunResource(DiceRunDataService diceRunDataService) {
        this.diceRunDataService = diceRunDataService;
    }

    @GetMapping(value = "/dicerun/total-grouped", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<DiceRunGroupingDto> getTotalGrouped() {
        return diceRunDataService.getAllGroupedBySidesAndDices();
    }

    @GetMapping(value = "/dicerun/relative-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Double> getRelativeDistributionOfSums(@NotNull @RequestParam int numberOfDices, @NotNull @RequestParam int numberOfSides) {
        return diceRunDataService.getRelativeDistributionOfSums(numberOfDices, numberOfSides);
    }
}
