package mvi.diceapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiceRunGroupingDto {
    private long totalNumberOfSimulations;
    private long totalRolls;
    private int byDices;
    private int bySides;
}
