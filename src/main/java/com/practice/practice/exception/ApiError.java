package com.practice.practice.exception;

public record ApiError(
        String timestamp,
        int status,
        String error,
        String message
) {}