package com.example.diplomnabe.Authentication;

import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class AuthenticationResponse
{
    private String token;
}
