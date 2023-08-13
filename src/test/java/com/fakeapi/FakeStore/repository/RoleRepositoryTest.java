package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role sampleRole;

    @BeforeEach
    public void setUp() {
        sampleRole = new Role();
        sampleRole.setName("ROLE_USER");
    }

    @Test
    public void testSaveRole() {
        Role savedRole = roleRepository.save(sampleRole);

        assertNotNull(savedRole.getId());
        assertEquals(sampleRole.getName(), savedRole.getName());
    }
}
