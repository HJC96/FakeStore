package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

