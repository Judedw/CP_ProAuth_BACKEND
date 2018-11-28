package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.product.dto.request.UserUpdateRequest;
import com.clearpicture.platform.product.entity.user_authenticate.User;
import com.clearpicture.platform.product.dto.request.UserCreateRequest;
import com.clearpicture.platform.product.service.user_authenticate.UserService;
import com.clearpicture.platform.product.dto.response.*;
import com.clearpicture.platform.product.util.TempCryptoService;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 * Created by Buddhi on 11/28/18.
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    //    for testing
    @Autowired
    private TempCryptoService tempCryptoService;

    @PostMapping("${app.endpoint.usersCreate}")
    public ResponseEntity<SimpleResponseWrapper<UserCreateResponse>> create(@Validated @RequestBody UserCreateRequest request) {


        User newUser = modelMapper.map(request, User.class);
        newUser.setPassword(tempCryptoService.encryptUserPassword(newUser.getPassword()));
        User savedUser = userService.save(newUser);


        UserCreateResponse response = modelMapper.map(savedUser, UserCreateResponse.class);
        return new ResponseEntity<SimpleResponseWrapper<UserCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);

    }


    @PostMapping("${app.endpoint.usersUpdate}")
    public ResponseEntity<SimpleResponseWrapper<UserUpdateResponse>> update(@PathVariable String id, @PathVariable String type,
                                                                            @Validated @RequestBody UserUpdateRequest request) {
        Long userId = cryptoService.decryptEntityId(id);
        User user = modelMapper.map(request, User.class);

        user.setId(userId);

        if (type.equals("PasswordSetting")) {
            user.setPassword(tempCryptoService.encryptUserPassword(user.getPassword()));
        }

        User updatedUser = userService.update(user, type);

        UserUpdateResponse response = modelMapper.map(updatedUser, UserUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<UserUpdateResponse>>(new SimpleResponseWrapper<UserUpdateResponse>(response), HttpStatus.OK);

    }

    @GetMapping("${app.endpoint.usersView}")
    public ResponseEntity<SimpleResponseWrapper<UserViewResponse>> retrieve(@PathVariable String id) {

        Long userId = cryptoService.decryptEntityId(id);

        User retrievedUser = userService.retrieve(userId);

        retrievedUser.setPassword(tempCryptoService.decryptUserPassword(retrievedUser.getPassword()));

        UserViewResponse response = modelMapper.map(retrievedUser,UserViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<UserViewResponse>>(new SimpleResponseWrapper<UserViewResponse>(response),HttpStatus.OK);

    }

    @GetMapping("${app.endpoint.usersList}")
    public ResponseEntity<ListResponseWrapper<UserViewResponse>> allUsers() {
        List<User> users = userService.allUsers();
        for (User u : users) {
            u.setPassword(tempCryptoService.decryptUserPassword(u.getPassword()));
        }

        List<UserViewResponse> userSuggestions = modelMapper.map(users, UserViewResponse.class);

        return new ResponseEntity<ListResponseWrapper<UserViewResponse>>(
                new ListResponseWrapper<UserViewResponse>(userSuggestions), HttpStatus.OK);
    }


}
