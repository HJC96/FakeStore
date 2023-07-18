package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testRoleRepository() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
    }
}