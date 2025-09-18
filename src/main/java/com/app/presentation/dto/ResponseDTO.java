package com.app.presentation.dto;

public record ResponseDTO<T>(int status, String message, T data) { }
