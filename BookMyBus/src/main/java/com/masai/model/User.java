package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userLoginId;

	@NotBlank(message = "Username is mandatory")
	@Column(unique = true)
	private String userName;

	@Size(min = 6, max = 10, message = "Password length must be between 6 and 10 characters")
	private String password;

	@NotBlank
	private String name;

	@NotBlank(message = "Contact is mandatory")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number should be exactly ten digits")
	private String contact;

	@Email(regexp = "[a-z0-9._]+@[a-z0-9._]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email must be in a valid format")
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Reservation> reservations = new ArrayList<Reservation>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks = new ArrayList<Feedback>();

	@NotBlank(message = "Role is mandatory")
	private String role;

	private boolean deleted = false;

	public User(@NotBlank(message = "Username is mandatory") String userName,
			@Size(min = 6, max = 10, message = "Password length must be between 6 and 10 characters") String password,
			String name,
			@NotBlank(message = "Contact is mandatory") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number should be exactly ten digits") String contact,
			@Email(regexp = "[a-z0-9._]+@[a-z0-9._]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = "Email must be in a valid format") String email,
			@NotBlank(message = "Role is mandatory") String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.role = role;
	}
}
