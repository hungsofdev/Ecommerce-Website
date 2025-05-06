package com.example.ecommerce_website.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private String username;
    private String fullname;
    private String email;
    private String photo;
    private List<String> roles;
}