package com.d4xaris.bookstore.security;

import com.d4xaris.bookstore.model.enums.Role;
import com.d4xaris.bookstore.repo.ClientRepository;
import com.d4xaris.bookstore.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);

        return clientRepository.findByEmail(email)
                .<UserDetails>map(c -> new CustomUserDetails(
                        c.getId(), c.getEmail(), c.getName(), c.getPassword(), Role.CUSTOMER))
                .or(() -> employeeRepository.findByEmail(email)
                        .map(e -> new CustomUserDetails(
                                e.getId(), e.getEmail(), e.getName(), e.getPassword(), e.getRole())))
                .orElseThrow(() -> {
                    log.warn("User not found: {}", email);
                    return new UsernameNotFoundException("User not found");
                });
    }
}