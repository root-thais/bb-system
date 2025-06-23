package com.barber.systembarber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDTO {
    public Long id;
    public String name_services;
    public String description_services;
    public Number price_services;
    public String available_times;
}