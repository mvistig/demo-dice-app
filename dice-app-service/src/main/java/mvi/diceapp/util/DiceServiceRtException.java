package mvi.diceapp.util;

import lombok.Getter;

/**
 * Runtime Exception for the Error Advice
 */
public class DiceServiceRtException extends RuntimeException {
    @Getter
    private final String origin;

    public DiceServiceRtException(String message, String origin) {
        super(message);
        this.origin = origin;
    }
}
