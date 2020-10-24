package mvi.diceapp.request;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@SequenceGenerator(name = "SEQ_DICE_ID", allocationSize = 1)
public class DiceRun {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DICE_ID")
    private Long id;

    private Integer numberOfRolls;
    private Integer numberOfDices;
    private Integer sidesOfDice;
    private String resultMap;
}
