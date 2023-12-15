package com.masai.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "user_id")
	private Integer userLoginId;

	@Column(unique = true)
	private String userName;

	@Size(min = 6, message = "Password length must be between 6 to 10 character")
	@Size(max = 10, message = "Password length must be between 6 to 10 character")
	private String password;

	private String firstName;

	private String lastName;
	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone Number should be exactly ten digits")
	private String contact;

	@Email(regexp = "[a-z0-9._]+@[a-z0-9._]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = "Email must be in a valid format")
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Reservation> reservation = new HashSet<Reservation>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

}
