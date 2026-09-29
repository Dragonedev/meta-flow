package com.dragone.meta_flow.service;

import com.dragone.meta_flow.database.model.UserEntity;
import com.dragone.meta_flow.database.repository.IUserRepository;
import com.dragone.meta_flow.dto.user.UserRequest;
import com.dragone.meta_flow.dto.user.UserResponse;
import com.dragone.meta_flow.exception.UserAlreadyExistsException;
import com.dragone.meta_flow.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest){
        String email = userRequest.email().trim().toLowerCase();
        // Validar email
        userRepository.findByEmail(userRequest.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("email already exists");
                });

        UserEntity user = UserEntity.builder()
                .name(userRequest.name())
                .email(email)
                .password(userRequest.password())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        UserEntity savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    public UserResponse updateUser(Integer id, UserRequest userRequest){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setUpdatedAt(LocalDate.now());

        UserEntity savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    private UserResponse toResponse(UserEntity user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
