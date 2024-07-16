package hr.dice.luka_jurkic.service.exceptions.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ErrorResponse {
    private final String errorKey;
    private final String message;
}

