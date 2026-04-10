package com.atmos.auth_service.service;

import com.atmos.auth_service.model.UserEntity;
import com.atmos.auth_service.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> object = userRepository.findByEmail(email);
        if (object.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        String password = object.get().getPassword();

        return User.builder()
                .username(object.get().getEmail())
                .password(password)
                .roles(object.get().getRole() != null ? object.get().getRole() : "USER")
                .build();
    }
}
