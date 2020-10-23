package mvi.diceapp.util;

import lombok.Data;

@Data
public class ErrorDto {
    private String origin;
    private String message;
}
