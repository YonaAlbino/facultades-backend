package com.example.facultades.service;

import com.example.facultades.generics.GenericService;
import com.example.facultades.model.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService extends GenericService<RefreshToken, Long> implements IRefreshTokenService{
}
