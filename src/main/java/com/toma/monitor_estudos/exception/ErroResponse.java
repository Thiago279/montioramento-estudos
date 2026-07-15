package com.toma.monitor_estudos.exception;

import java.time.LocalDateTime;

public record ErroResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
