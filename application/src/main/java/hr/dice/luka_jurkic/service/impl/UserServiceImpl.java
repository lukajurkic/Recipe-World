package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.mapper.UserMapper;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.persistence.entity.UserRole;
import hr.dice.luka_jurkic.persistence.repository.UserRepository;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.dto.UserDetailsDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;
import hr.dice.luka_jurkic.service.UserService;
import hr.dice.luka_jurkic.service.exceptions.UserServiceException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDTO getUser(String username) {
        return userMapper.toDto(fetchUser(username));
    }

    @Override
    public UserDetailsDTO getUserDetails(String username) {
        return userMapper.toDetailedDto(fetchUser(username));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    public List<UserDetailsDTO> getUsersDetails() {
        return userMapper.toDetailedDto(userRepository.findAll());
    }

    @Override
    public List<String> getUserRoles() {
        return Stream.of(UserRole.values()).map(UserRole::name).collect(Collectors.toList());
    }

    @Override
    public UserDetailsDTO createUser(UserRequest userRequest) {
        checkUniqueness(userRequest);
        UserEntity user = userMapper.requestToEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(UserRole.USER);
        user = userRepository.save(user);
        cacheUser(user);
        return userMapper.toDetailedDto(user);
    }

    @Override
    public void deleteUser(String username) {
        inMemoryUserDetailsManager.deleteUser(username);
        userRepository.delete(fetchUser(username));
    }

    @Override
    public UserDetailsDTO updateUser(String username, UserRequest userRequest) {
        UserEntity user = fetchUser(username);
        if(!Objects.equals(username, userRequest.getUsername())) {
            checkUniqueness(userRequest);
        }
        inMemoryUserDetailsManager.deleteUser(username);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setUsername(userRequest.getUsername());
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setDateOfBirth(convertToLocalDate(userRequest.getDateOfBirth()));
        cacheUser(user);
        return userMapper.toDetailedDto(userRepository.save(user));
    }

    @Override
    public UserDetailsDTO upgradeUserRights(String username) {
        UserEntity user = fetchUser(username);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        inMemoryUserDetailsManager.deleteUser(username);
        if(Objects.equals(fetchUser(authentication.getName()).getRole(), UserRole.ADMIN)) {
            user.setRole(UserRole.ADMIN);
            userRepository.save(user);
        }
        cacheUser(user);
        return userMapper.toDetailedDto(user);
    }

    @Override
    public UserDetailsDTO downgradeUserRights() {
        checkAdminCount();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = fetchUser(authentication.getName());
        inMemoryUserDetailsManager.deleteUser(authentication.getName());
        user.setRole(UserRole.USER);
        cacheUser(user);
        return userMapper.toDetailedDto(userRepository.save(user));
    }

    @Override
    public boolean isUnique(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$") && password.length() >= 8 && password.length() <= 20;
    }

    private UserEntity fetchUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserServiceException::notFound);
    }

    private void checkUniqueness(UserRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw UserServiceException.alreadyExists("User with username " + user.getUsername() + " already exists.");
        });
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private void checkAdminCount() {
        List<UserEntity> admins = userRepository.findByRole(UserRole.ADMIN);
        if (admins.size() == 1){
            throw UserServiceException.isLastAdmin("You are the last admin!");
        }
    }

    private void cacheUser(UserEntity user) {
        UserDetails newUserDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
        inMemoryUserDetailsManager.createUser(newUserDetails);
    }
}
