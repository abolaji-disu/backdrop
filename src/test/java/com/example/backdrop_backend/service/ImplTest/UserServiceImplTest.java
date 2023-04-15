package com.example.backdrop_backend.service.ImplTest;

import com.example.backdrop_backend.data.model.AccountUser;
import com.example.backdrop_backend.data.repository.UserRepo;
import com.example.backdrop_backend.dtos.request.AddUserAccountRequest;
import com.example.backdrop_backend.dtos.request.GetUserAccountRequest;
import com.example.backdrop_backend.service.Impl.UserServiceImpl;
import com.example.backdrop_backend.service.PaystackService;
import com.example.backdrop_backend.utils.matchNames.ConfirmName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.io.IOException;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private PaystackService paystackService;
    @Mock
    private ConfirmName confirmName;
    @InjectMocks
    private UserServiceImpl userService;
    private final String TEST_ACCOUNT_NUMBER = "2111265871";
    private final String TEST_ACCOUNT_NAME = "SULAIMON ABOLAJI DISU";
    private final String TEST_BANK_CODE = "057";
    private AccountUser expectedAccountUser;


    @BeforeEach
    public void setUp() {
        expectedAccountUser = new AccountUser();
        expectedAccountUser.setAccountNumber(TEST_ACCOUNT_NUMBER);
        expectedAccountUser.setBankCode(TEST_BANK_CODE);
        expectedAccountUser.setAccountName(TEST_ACCOUNT_NAME);
    }
    @Test
    void userThatDidNotProvideNameThatMatchPaystackAPIShouldBeSavedInTheDatabaseWithFalseVerification() throws IOException {
        expectedAccountUser.setIs_verified(false);
        String NAME = "SULAIMON DISU";
        // Set up mock behavior
        when(userRepo.findUserByAccountNumber(TEST_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        when(paystackService.paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE))).thenReturn(TEST_ACCOUNT_NAME);
        when(confirmName.isMatch(eq(TEST_ACCOUNT_NAME), eq(NAME))).thenReturn(false);
        when(userRepo.save(any(AccountUser.class))).thenReturn(expectedAccountUser);

        // Call the method being tested
        AddUserAccountRequest addUserAccountRequest = new AddUserAccountRequest(TEST_ACCOUNT_NUMBER, TEST_BANK_CODE, TEST_ACCOUNT_NAME);
        userService.addUserBankAccount(addUserAccountRequest);

        // Verify the results
        assertNotNull(expectedAccountUser);
        assertEquals(TEST_ACCOUNT_NAME, expectedAccountUser.getAccountName());
        assertFalse(expectedAccountUser.getIs_verified());

        // Verify that the paystackValidation method was called correctly
        verify(paystackService, times(1)).paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE));
    }

    @Test
    void testThatAnAccountForExistingUserCanBeRetrieved() throws IOException {
        expectedAccountUser.setIs_verified(true);

        when(userRepo.findUserByAccountNumber(TEST_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        GetUserAccountRequest getUserAccountRequest = new GetUserAccountRequest();
        getUserAccountRequest.setAccountNumber(TEST_ACCOUNT_NUMBER);
        getUserAccountRequest.setBankCode(TEST_BANK_CODE);
        userService.getUserAccountName(getUserAccountRequest);

        // Verify the results
        assertNotNull(expectedAccountUser);
        assertEquals(TEST_ACCOUNT_NAME, expectedAccountUser.getAccountName());
        assertTrue(expectedAccountUser.getIs_verified());
    }
    @Test
    void ifNameInputedByUserIsNotAvailableItReturnTheOneVerifiedByPayStack() throws IOException {
        String test_account_number = "2116471341";
        String test_bank_code = "033";
        String test_bank_name = "JOSEPH RACHAEL ABIMBOLA";
        String expectedName = "JOSEPH RACHAEL ABIMBOLA";
        when(userRepo.findUserByAccountNumber(test_account_number)).thenReturn(Optional.empty());


        when(paystackService.paystackValidation(eq(test_account_number),eq(test_bank_code))).thenReturn(test_bank_name);
        GetUserAccountRequest getUserAccountRequest = new GetUserAccountRequest();
        getUserAccountRequest.setAccountNumber(TEST_ACCOUNT_NUMBER);
        getUserAccountRequest.setBankCode(TEST_BANK_CODE);
        userService.getUserAccountName(getUserAccountRequest);

        assertNotNull(expectedAccountUser);
        assertEquals(test_bank_name, expectedName);
    }

    @Test
    void addUserAccountThatMatchesPaystackApi__AndAlsoWithinThelevenshteinDistanceIsSavedWithStatusVerified() throws IOException {
        // Set up test data
        expectedAccountUser.setIs_verified(true);

        // Set up mock behavior
        when(userRepo.findUserByAccountNumber(TEST_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        when(paystackService.paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE))).thenReturn(TEST_ACCOUNT_NAME);
        when(confirmName.isMatch(eq(TEST_ACCOUNT_NAME), eq(TEST_ACCOUNT_NAME))).thenReturn(true);
        when(userRepo.save(any(AccountUser.class))).thenReturn(expectedAccountUser);

        // Call the method being tested
        AddUserAccountRequest addUserAccountRequest = new AddUserAccountRequest(TEST_ACCOUNT_NUMBER, TEST_BANK_CODE, TEST_ACCOUNT_NAME);
        userService.addUserBankAccount(addUserAccountRequest);

        // Verify the results
        assertNotNull(expectedAccountUser);
        assertEquals(TEST_ACCOUNT_NAME, expectedAccountUser.getAccountName());
        assertTrue(expectedAccountUser.getIs_verified());

        // Verify that the paystackValidation method was called correctly
        verify(paystackService, times(1)).paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE));
    }




}