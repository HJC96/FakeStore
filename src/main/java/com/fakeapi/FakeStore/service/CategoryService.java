package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.dto.CartDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public interface CategoryService {

    List<Category> list();
    Optional<Category> read(Long id) ;


}
