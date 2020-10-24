package mvi.diceapp.request;

import lombok.Data;

@Data
public class DiceRunGroupingDto {
    private long totalNumberOfSimulations;
    private long totalRolls;
    private int byDices;
    private int bySides;
}
