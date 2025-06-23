package com.barber.systembarber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public Long id;
    public String username;
    public String password;
    public String email;
    public String phone;
    public String feedback;

}
