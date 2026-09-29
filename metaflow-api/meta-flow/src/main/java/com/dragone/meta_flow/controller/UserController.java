package com.dragone.meta_flow.controller;

import com.dragone.meta_flow.dto.user.UserRequest;
import com.dragone.meta_flow.dto.user.UserResponse;
import com.dragone.meta_flow.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }

    @GetMapping
    public Page<UserResponse> getUsers(Pageable pageable){
        return userService.getUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@Positive @PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@Positive @PathVariable Integer id,
                                   @Valid  @RequestBody UserRequest userRequest){
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@Positive @PathVariable Integer id){
        userService.deleteUser(id);
    }
    
}
