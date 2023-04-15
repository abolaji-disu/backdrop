package com.example.backdrop_backend.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserAccountRequest {
    private String accountNumber;
    private String bankCode;
}
