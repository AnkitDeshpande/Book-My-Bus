package com.masai.model;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
	@NotNull(message ="Username cannot be null.")
	private String username;
	@NotNull(message ="Password cannot be null.")
	private String password;
}
