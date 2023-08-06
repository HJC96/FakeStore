package com.fakeapi.FakeStore.service;


import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public interface ProductService {
    Product register(ProductDTO productDTO);
    ProductDTO read(Long id);

    PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductDTO> list_limit(PageRequestDTO pageRequestDTO, int limit);

}
