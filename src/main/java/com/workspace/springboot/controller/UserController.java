package com.workspace.springboot.controller;

import com.workspace.springboot.dto.UserDto;
import com.workspace.springboot.entity.User;
import com.workspace.springboot.exception.ErrorDetails;
import com.workspace.springboot.exception.ResourceNotFoundException;
import com.workspace.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

//API Documentation
@Tag(name = "CRUD REST APIs for User Resource",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get all Users, Delete User")
@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    //Create user REST API
    @Operation(summary = "Create User REST API",
            description = "Create User REST API is used to save user in database")
    @ApiResponse(responseCode = "201",
            description = "Http Status 201 CREATED")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //Create GET user REST API
    @Operation(summary = "Get User REST API",
            description = "Get User REST API is used to get single user from database")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Create GET all users REST API
    @Operation(summary = "Get All Users REST API",
            description = "Get All Users REST API is used to get all users from database")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    //Create UPDATE users REST API
    @Operation(summary = "Update User REST API",
            description = "Update user REST API is used to update a particular in database")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @Valid @RequestBody UserDto user) {
        user.setId(userId);
        UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //Create Delete user REST API
    @Operation(summary = "Delete User REST API",
            description = "Delete User REST API is used to delete a particular user in database")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    // Exception for Controller.
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                                        WebRequest webRequest) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "USER_NOT_FOUND"
//        );
//        return  new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
//    }
}
