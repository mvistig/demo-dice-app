package mvi.diceapp.rest;

import mvi.diceapp.data.DiceRunDataService;
import mvi.diceapp.request.DiceRunGroupingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiceRunResource {
    @Autowired
    private DiceRunDataService diceRunDataService;

    @GetMapping("/dicerun/total-grouped")
    @ResponseStatus(HttpStatus.OK)
    public List<DiceRunGroupingDto> getTotalGrouped() {
        return diceRunDataService.getAllGroupedBySidesAndDices();
    }
}
