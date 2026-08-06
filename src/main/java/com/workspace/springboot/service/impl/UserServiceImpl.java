package com.workspace.springboot.service.impl;

import com.workspace.springboot.dto.UserDto;
import com.workspace.springboot.entity.User;
import com.workspace.springboot.mapper.AutoUserMapper;
import com.workspace.springboot.mapper.UserMapper;
import com.workspace.springboot.repository.UserRepository;
import com.workspace.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //To convert UserDto into JPA entity

        // User user = new User(
        //                userDto.getId(),
        //                userDto.getFirstName(),
        //                userDto.getLastName(),
        //                userDto.getEmail());

        // Use custom UserMapper to map UserDto into JPA entity
        // User user = UserMapper.mapToUser(userDto);

        // Use ModelMapper to map UserDto into JPA entity
        // User user = modelMapper.map(userDto,User.class);

        // Use MapStruct-> AutoUserMapper to map UserDto into JPA entity
        User user = AutoUserMapper.MAPPER.mapToUser(userDto);

        User savedUser = userRepository.save(user);

        //To convert JPA entity to UserDto

        // UserDto savedUserDto = new UserDto(
        //                savedUser.getId(),
        //                savedUser.getFirstName(),
        //                savedUser.getLastName(),
        //                savedUser.getEmail());

        // Use custom UserMapper to map JPA entity into UserDto.
        // UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);

        // Use ModelMapper to map JPA entity into UserDto.
        // UserDto savedUserDto = modelMapper.map(savedUser,UserDto.class);

        // Use MapStruct-> AutoUserMapper to map JPA entity into UserDto
        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        //return UserMapper.mapToUserDto(user);
        //return modelMapper.map(optionalUser,UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        //return users.stream().map(UserMapper::mapToUserDto)
        //        .collect(Collectors.toList());
        //return users.stream().map(user -> modelMapper.map(user,UserDto.class))
        //        .collect(Collectors.toList());
        return users.stream().map(user -> AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updatedUser);
        //return modelMapper.map(updatedUser,UserDto.class);
        return  AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
