package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Ensure default roles exist
        Role adminRole = getOrCreateRole("ROLE_ADMIN");
        Role customerRole = getOrCreateRole("ROLE_CUSTOMER");

        // 2. Ensure a default admin user exists
        String defaultAdminEmail = "admin@ecommerce.com";
        if (!userRepository.existsByEmail(defaultAdminEmail)) {
            User admin = new User();
            admin.setName("System Administrator");
            admin.setEmail(defaultAdminEmail);
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setPhone("0000000000");
            admin.setAddress("Headquarters");
            admin.setRole(adminRole);
            userRepository.save(admin);
            System.out.println(">>> [DataInitializer] Default admin created: " + defaultAdminEmail + " / Admin@123");
        }
    }

    private Role getOrCreateRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            // Also check without "ROLE_" prefix in case existing DB has "ADMIN" / "CUSTOMER"
            String altName = roleName.startsWith("ROLE_") ? roleName.substring(5) : "ROLE_" + roleName;
            role = roleRepository.findByRoleName(altName);
        }
        if (role == null) {
            role = new Role(roleName);
            role = roleRepository.save(role);
            System.out.println(">>> [DataInitializer] Seeded role: " + roleName);
        }
        return role;
    }
}
