package mvi.diceapp.data;

import mvi.diceapp.request.DiceRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiceRunRepository extends JpaRepository<DiceRun, Long> {

    /**
     * @return a list of results, but the results are in a form of an array of Object in the order of declaration in the select
     */
    @Query("select sum(d.numberOfRolls), count(d), d.sidesOfDice, d.numberOfDices from DiceRun d group by d.sidesOfDice, d.numberOfDices")
    public List<Object[]> getSumOfRollsGrouped();

    public List<DiceRun> getAllByNumberOfDicesAndSidesOfDiceOrderById(int numberOfDices,
                                                                      int sidesOfDices);


    @Query("select distinct d.numberOfDices, d.numberOfRolls from DiceRun d")
    public List<Object> getDistinct();

}
