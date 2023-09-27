package org.shameenakoodan.KanbanFlowUserService.security;

import org.shameenakoodan.KanbanFlowUserService.model.User;
import org.shameenakoodan.KanbanFlowUserService.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User student = userRepository.findByEmail(usernameOrEmail);
        if(student != null) {
            return new org.springframework.security.core.userdetails.User(student.getEmail(),
                    student.getPassword(),
                    student.getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));
        }else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

}
