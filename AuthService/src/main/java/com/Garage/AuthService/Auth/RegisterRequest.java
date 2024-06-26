package com.Garage.AuthService.Auth;


import com.Garage.AuthService.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
