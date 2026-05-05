package com.aastmt.renovation.dto;

import com.aastmt.renovation.model.Role;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

   
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 3, message = "First name must be at least 3 characters")
    private String fname;

    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lname;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be at least 3 characters")
    private String username;


   @NotBlank(message = "email is required")
    private String email;


    private String profession; 

    private Role role;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Size(min = 11, message = "InCorrect PhoneNumber form")
    private String phonenumber;

    @AssertTrue(message = "Profession is required for workers (must be electrician, carpenter, or plumber).")
    public boolean isValidProfession() {
        
        if (this.role == Role.worker) {
            
            if (this.profession == null || this.profession.trim().isEmpty()) {
                return false;
            }
            
            String prof = this.profession.toLowerCase();
            return prof.equals("electrician") || prof.equals("carpenter") || prof.equals("plumber");
        }
        
        
        return true; 
    }

    public String getFname() { return fname; }

    public void setFname(String fname) { this.fname = fname;}

    public String getLname() {return lname;}

    public void setLname(String lname) {this.lname = lname; }

    public String getUsername() {return username;}

    public void setUsername(String username) { this.username = username; }

    public String getProfession() {return profession;}

    public void setProfession(String profession) {this.profession = profession;}

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getPhonenumber() {return phonenumber;}

    public void setPhonenumber(String phonenumber) {this.phonenumber = phonenumber;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

   
}