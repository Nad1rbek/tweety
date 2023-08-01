package com.nad1r.service;

import com.nad1r.exception.NotFoundException;
import com.nad1r.model.Role;
import com.nad1r.model.User;
import com.nad1r.repository.UserRepository;
import com.nad1r.request.RegisterRequest;
import com.nad1r.request.UpdateUserRequest;
import com.nad1r.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserResponse create(RegisterRequest request){
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthday(request.getBirthday())
                .role(Role.USER).build();
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    public Page<UserResponse> getUsers(Pageable page){
        return userRepository.findAll(page)
                .map(x -> modelMapper.map(x, UserResponse.class));
    }

    public UserResponse getUserById(long id){
        User user =  findUserById(id);
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse getUserByUsername(String username){
        return modelMapper.map(findUserByUsername(username), UserResponse.class);
    }

    public UserResponse updateUserById(long id, UpdateUserRequest request){
        User user = findUserById(id);
        user.setName(Optional.ofNullable(request.getName()).orElse(user.getName()));
        user.setBio(Optional.ofNullable(request.getBio()).orElse(user.getBio()));
        user.setLocation(Optional.ofNullable(request.getLocation()).orElse(user.getLocation()));
        user.setWebsite(Optional.ofNullable(request.getWebSite()).orElse(user.getWebsite()));
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    public void deleteUserById(long id){
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public void updateUserProfileImage(String file, String username){
        User user = findUserByUsername(username);
        user.setProfileImageLink(file);
        userRepository.save(user);
    }

    protected User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    protected User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
    }
}
