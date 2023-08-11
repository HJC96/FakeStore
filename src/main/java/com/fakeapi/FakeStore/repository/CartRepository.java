package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Cart;
import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.repository.search.CartSearch;
import com.fakeapi.FakeStore.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> , CartSearch {
//    List<Cart> findAllByCartName(String name);
    Page<Cart> findAll(Pageable pageable);



}
