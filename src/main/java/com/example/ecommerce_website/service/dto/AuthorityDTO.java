package com.example.ecommerce_website.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityDTO {
    private Long id;
    private String username;
    private String roleId;
    private String roleName;
}