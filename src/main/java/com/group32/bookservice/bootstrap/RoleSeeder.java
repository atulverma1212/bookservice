package com.group32.bookservice.bootstrap;

import com.group32.bookservice.model.Role;
import com.group32.bookservice.model.RoleEnum;
import com.group32.bookservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {

        Arrays.stream(RoleEnum.values()).forEach((role) -> {
            Optional<Role> optionalRole = roleRepository.findByName(role);
            optionalRole.ifPresentOrElse(e -> log.info("Role: {} is present already", e),
                    () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(role);
                roleToCreate.setDescription(role.getDescription());
                roleRepository.save(roleToCreate);
            });
        });
    }
}
