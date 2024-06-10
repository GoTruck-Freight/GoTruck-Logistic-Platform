package com.gotruck.shipperservice.exceptions;


import lombok.*;

@Builder
public record ErrorResponse(String message) {
}

