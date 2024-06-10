package com.gotruck.truckcategoryservice.exceptions;

import lombok.*;

@Builder
public record ErrorResponse(String message) {
}