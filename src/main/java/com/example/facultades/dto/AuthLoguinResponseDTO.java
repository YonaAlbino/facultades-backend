package com.example.facultades.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message","jwt", "status"})
public record AuthLoguinResponseDTO(String nombre,
                                    String mensaje,
                                    String token,
                                    Boolean status) {
}
