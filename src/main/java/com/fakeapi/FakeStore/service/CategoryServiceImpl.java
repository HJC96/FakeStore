package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> read(Long id) {
        return categoryRepository.findById(id);
    }


}
