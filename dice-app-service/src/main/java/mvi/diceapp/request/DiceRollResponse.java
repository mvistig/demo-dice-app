package mvi.diceapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DiceRollResponse {

    List<HitEntry> distribution;

    @Data
    @AllArgsConstructor
    public static class HitEntry {
        long sum;
        int hits;
    }

    public static DiceRollResponse of(Map<Long, Integer> hitMap) {
        val response = new DiceRollResponse();
        response.distribution = new ArrayList<>();
        hitMap.forEach((sum, hit) -> response.distribution.add(new HitEntry(sum, hit)));
        return response;
    }
}
