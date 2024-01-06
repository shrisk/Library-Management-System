package com.lms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonRequest {

	@NotNull
	@NotEmpty
    private String name;
	
	@NotNull
	@NotEmpty
	@Email(message = "invalid email format")
    private String email;
	
	@NotNull
	@NotEmpty
	@Size(min = 4, max = 15, message = "password should be between 4 to 15 char")
    private String password;

    public PersonRequest() {
    }

    public PersonRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
