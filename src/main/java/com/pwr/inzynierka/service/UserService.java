package com.pwr.inzynierka.service;

import com.pwr.inzynierka.dto.UserDTO;
import com.pwr.inzynierka.model.User;
import com.pwr.inzynierka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> getByPublicKey(String publicKey) {
        return repository.findByPublicKey(publicKey);
    }

    public Optional<User> getByName(String name) {
        return repository.findByName(name);
    }

    public User save(UserDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .publicKey(userDTO.getPublicKey())
                .otpKeys(new HashSet<>())
                .build();

        return repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }
}
