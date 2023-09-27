package org.shameenakoodan.KanbanFlowUserService.impl;
import org.shameenakoodan.KanbanFlowUserService.dto.UserDTO;
import org.shameenakoodan.KanbanFlowUserService.model.Role;
import org.shameenakoodan.KanbanFlowUserService.model.User;
import org.shameenakoodan.KanbanFlowUserService.repository.RoleRepository;
import org.shameenakoodan.KanbanFlowUserService.repository.UserRepository;
import org.shameenakoodan.KanbanFlowUserService.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveStudent(UserDTO userDTO) {
        User user = new User();

        user.setName(userDTO.getFirstName() + " " + userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        // Encrypt the password using Spring Security
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findStudentByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllStudents() {
        List<User> students = userRepository.findAll();

        return students.stream()
                .map((user) -> mapToStudentDto(user))
                .collect(Collectors.toList());
    }

    private UserDTO mapToStudentDto(User user) {
        UserDTO userDTO = new UserDTO();

        String[] str = user.getName().split(" ");
        userDTO.setFirstName(str[0]);
        userDTO.setLastName(str[1]);
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}