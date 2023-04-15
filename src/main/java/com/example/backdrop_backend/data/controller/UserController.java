package com.example.backdrop_backend.data.controller;

import com.example.backdrop_backend.dtos.request.AddUserAccountRequest;
import com.example.backdrop_backend.dtos.request.GetUserAccountRequest;
import com.example.backdrop_backend.dtos.response.ApiData;
import com.example.backdrop_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @MutationMapping
    public ApiData addUserAccount(@Argument AddUserAccountRequest addUserAccountRequest) throws IOException {
        return userService.addUserBankAccount(addUserAccountRequest);
    }

    @QueryMapping
    public ApiData getUserAccount(@Argument GetUserAccountRequest getUserAccountRequest) throws IOException {
        return userService.getUserAccountName(getUserAccountRequest);

    }
}
