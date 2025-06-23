package com.barber.systembarber.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
}
