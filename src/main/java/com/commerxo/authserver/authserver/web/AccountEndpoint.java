package com.commerxo.authserver.authserver.web;

import com.commerxo.authserver.authserver.common.APIResponse;
import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.dto.UserRegistrationRequest;
import com.commerxo.authserver.authserver.dto.UserRegistrationResponse;
import com.commerxo.authserver.authserver.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.commerxo.authserver.authserver.common.WebConstants.Account;

@RestController
@RequestMapping(Account.ACCOUNT)
public class AccountEndpoint {

    private final UserRegistrationService registrationService;

    public AccountEndpoint(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = Account.USER_ACCOUNT_REGISTER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<UserRegistrationResponse>> registerUser(@RequestBody @Valid UserRegistrationRequest registrationRequest){
        RegisteredUser registeredUser = this.registrationService.createUser(registrationRequest);
        APIResponse<UserRegistrationResponse> apiResponse = new APIResponse<>(
                HttpStatus.CREATED,
                UserRegistrationResponse.mapFromEntity(registeredUser),
                "User Account Created Successfully!"
        );
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

}