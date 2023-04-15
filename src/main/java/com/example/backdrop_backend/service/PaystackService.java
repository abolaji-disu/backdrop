package com.example.backdrop_backend.service;

import java.io.IOException;

public interface PaystackService {
    String paystackValidation(String accountNumber, String bankCode) throws IOException;
}
