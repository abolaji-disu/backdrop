package com.example.backdrop_backend.service.ImplTest;

import com.example.backdrop_backend.service.Impl.PaystackServiceImpl;
import com.example.backdrop_backend.service.PaystackService;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PaystackServiceImplTest {
    private final PaystackService paystackService = new PaystackServiceImpl();

    @Test
    public void testThatNameAttributedToAParticularAccountNumberAndBankIsReturned() throws IOException {
        String TEST_ACCOUNT_NUMBER = "2111265871";
        String TEST_ACCOUNT_NAME = "SULAIMON ABOLAJI DISU";
        String TEST_BANK_CODE = "057";

        String verifiedAccountName = paystackService.paystackValidation(TEST_ACCOUNT_NUMBER, TEST_BANK_CODE);

        assertEquals(TEST_ACCOUNT_NAME, verifiedAccountName);
    }
}