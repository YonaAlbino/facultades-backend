package com.example.facultades.dto;

import com.example.facultades.model.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto extends BaseDTO<RefreshToken> {
    private Long id;
    private String token;
}
