package com.example.backdrop_backend.service;

import com.example.backdrop_backend.data.model.AccountUser;
import com.example.backdrop_backend.dtos.request.AddUserAccountRequest;
import com.example.backdrop_backend.dtos.request.GetUserAccountRequest;
import com.example.backdrop_backend.dtos.response.ApiData;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    Optional<AccountUser> findUserById(String userId);
    Optional<AccountUser> findUserByAccountNumber(String accountNumber);

    public ApiData addUserBankAccount(AddUserAccountRequest addUserAccountRequest) throws IOException;
    public ApiData getUserAccountName(GetUserAccountRequest getUserAccountRequest) throws IOException ;
}
