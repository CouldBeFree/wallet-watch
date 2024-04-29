package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.dto.UserResponseDto;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.model.Person;
import com.expenses.walletwatch.repository.UserRepository;
import com.expenses.walletwatch.utils.GetUserData;
import com.expenses.walletwatch.utils.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final GetUserData getUserData;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, GetUserData getUserData, UserRepository userRepository) {
        this.getUserData = getUserData;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto getLoggedInUser() {
        Long userId = getUserData.getUserIdFromToken();
        Person person = userRepository.findById(userId).orElseThrow(() -> new BadRequest("User not found"));
        return new UserResponseDto(
                person.getEmail(),
                person.getUsername(),
                person.getId()
        );
    }

    public void registerUser(UserRegistrationDto dto) {
        UserValidator UserToValidate = new UserValidator(dto.getUsername(), dto.getPassword(), dto.getEmail());
        try {
            UserToValidate.validateUser();
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
        Person ExistingUser = userRepository.findUserByEmailOrUsername(dto.getEmail(), dto.getUsername());
        if (ExistingUser != null) {
            throw new BadRequest("Email or Username already exists");
        }
        Person NewPerson = new Person();
        NewPerson.setEmail(dto.getEmail());
        NewPerson.setUsername(dto.getUsername());
        NewPerson.setPassword(passwordEncoder.encode((dto.getPassword())));
        userRepository.save(NewPerson);
    }
}
