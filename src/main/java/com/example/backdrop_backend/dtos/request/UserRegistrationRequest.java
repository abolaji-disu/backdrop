package com.example.backdrop_backend.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {

    private String accountNumber;
    private String accountName;
    private String bankCode;
}
