package mvi.diceapp.rest;

import lombok.val;
import mvi.diceapp.request.DiceRollRequest;
import mvi.diceapp.request.DiceRollResponse;
import mvi.diceapp.service.BasicDiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Random;

@RestController
public class DiceResource {

    private final BasicDiceService service;

    @Autowired
    public DiceResource(BasicDiceService service) {
        this.service = service;
    }

    @GetMapping(value = "/dice", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DiceRollResponse getBasicDiceRoll() {
        val hitMap = service.rollOfDice(100, 6, 3, new Random());
        return DiceRollResponse.of(hitMap);
    }

    @PostMapping(value = "/dice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DiceRollResponse postCustomDiceRoll(@Valid @NotNull @RequestBody DiceRollRequest request) {
        val hitMap = service.rollOfDice(request.getNumberOfRolls(), request.getSidesOfDice(), request.getNumberOfDices(), new Random());
        return DiceRollResponse.of(hitMap);
    }
}
