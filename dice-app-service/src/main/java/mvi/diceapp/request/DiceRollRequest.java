package mvi.diceapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiceRollRequest {
    @NotNull
    @Min(value = 1, message = "Number of Rolls must be GTE 1")
    @Max(value = Integer.MAX_VALUE, message = "Number of Rolls must be LT " + Integer.MAX_VALUE)
    private Integer numberOfRolls;
    @NotNull
    @Min(value = 1, message = "Dice Number must be GTE 1")
    @Max(value = Integer.MAX_VALUE, message = "Dice Number must be LT " + Integer.MAX_VALUE)
    private Integer numberOfDices;
    @NotNull
    @Min(value = 4, message = "Dice Number of Sides must be GTE 4")
    @Max(value = Integer.MAX_VALUE, message = "Dice Number of Sides  must be LT " + Integer.MAX_VALUE)
    private Integer sidesOfDice;
}
