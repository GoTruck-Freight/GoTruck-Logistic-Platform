package com.gotruck.orderservice.exceptions;

import lombok.*;

@Builder
public record ErrorResponse(String message) {
}
