package mvi.diceapp.rest;

import mvi.diceapp.request.DiceRollRequest;
import mvi.diceapp.service.BasicDiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Random;

@RestController
public class DiceResource {

    @Autowired
    private BasicDiceService service;

    @GetMapping("/dice")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Integer> getBasicDiceRoll() {
        return service.rollOfDice(100, 6, 3, new Random());
    }

    @PostMapping("/dice")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Integer> postCustomDiceRoll(@Valid @NotNull @RequestBody DiceRollRequest request) {
        return service.rollOfDice(request.getNumberOfRolls(), request.getSidesOfDice(), request.getNumberOfDices(), new Random());
    }
}
