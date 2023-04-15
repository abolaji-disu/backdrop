package com.example.backdrop_backend.service.Impl;

import com.example.backdrop_backend.data.model.AccountUser;
import com.example.backdrop_backend.data.repository.UserRepo;
import com.example.backdrop_backend.dtos.request.AddUserAccountRequest;
import com.example.backdrop_backend.dtos.request.GetUserAccountRequest;
import com.example.backdrop_backend.dtos.response.ApiData;
import com.example.backdrop_backend.exceptions.GenericException;
import com.example.backdrop_backend.service.PaystackService;
import com.example.backdrop_backend.service.UserService;
import com.example.backdrop_backend.utils.matchNames.ConfirmName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ConfirmName confirmName;

    private final PaystackService paystackService;

    @Override
    public Optional<AccountUser> findUserById(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<AccountUser> findUserByAccountNumber(String accountNumber) {
        return userRepo.findUserByAccountNumber(accountNumber);
    }

    @Override
    public ApiData addUserBankAccount(AddUserAccountRequest addUserAccountRequest) throws IOException {

        if(findUserByAccountNumber(addUserAccountRequest.accountNumber()).isPresent())
            throw new GenericException(String.format("User with %s already exist", addUserAccountRequest.accountNumber()));

        String foundAccountName= paystackService.paystackValidation(addUserAccountRequest.accountNumber(), addUserAccountRequest.bankCode());

        boolean nameMatches = confirmName.isMatch(addUserAccountRequest.accountName(), foundAccountName);

        AccountUser foundUser = saveUser(addUserAccountRequest);
        if(!nameMatches){
            return ApiData.builder()
                    .message("Name doesn't match")
                    .build();
        }
        userRepo.updateUser(foundUser.getAccountName());

        return ApiData.builder()
                .message(String.format("%s is VERIFIED", addUserAccountRequest.accountName()))
                .build();

    }
    private AccountUser saveUser(AddUserAccountRequest addUserAccountRequest) {

        AccountUser accountUser = AccountUser.builder()
                .accountNumber(addUserAccountRequest.accountNumber())
                .dateCreated(LocalDateTime.now())
                .accountName(addUserAccountRequest.accountName())
                .is_verified(false)
                .bankCode(addUserAccountRequest.bankCode())
                .build();
        return userRepo.save(accountUser);
    }

    @Override
    public ApiData getUserAccountName(GetUserAccountRequest getUserAccountRequest) throws IOException {

        Optional<AccountUser> accountUser = findUserByAccountNumber(getUserAccountRequest.getAccountNumber());
        if(accountUser.isPresent() && accountUser.get().getIs_verified()){

            return ApiData.builder().
                    message(WordUtils.capitalizeFully(accountUser.get().getAccountName()))
                    .build();
        }

        else{
            String foundAccountName = paystackService.paystackValidation(getUserAccountRequest.getAccountNumber(), getUserAccountRequest.getBankCode());

              return ApiData.builder()
                        .message(WordUtils.capitalizeFully(foundAccountName))
                        .build();
            }
    }

}
