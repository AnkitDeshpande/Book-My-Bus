package com.masai.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data, LocalDateTime.now());
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null, LocalDateTime.now());
	}
}
