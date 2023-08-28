package com.masai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminLoginId;

    @NotBlank(message = "Username is mandatory")
    @Column(unique = true)
    private String adminLoginName;

    @Size(min = 6, message = "Password length must be between 6 and 10 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    private String name;

    @NotBlank(message = "Contact is mandatory")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number should be exactly ten digits")
    private String contact;

    @Email(regexp = "[a-z0-9._]+@[a-z0-9._]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email must be in a valid format")
    private String email;

    @JsonIgnore
    private String role="ADMIN";

    @Column(nullable = false)
    private Boolean deleted=false;

    public Admin(Integer adminLoginId, String adminLoginName, String password, String name, String contact, String email, String role, Boolean deleted) {
        this.adminLoginId = adminLoginId;
        this.adminLoginName = adminLoginName;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.role = role;
        this.deleted = deleted;
    }
}
