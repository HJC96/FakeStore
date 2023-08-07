package com.fakeapi.FakeStore.service;


import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProductService {
    Product register(ProductDTO productDTO);
    ProductDTO read(Long id);

    List<ProductDTO> listByCategoryName(String name);

    PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductDTO> listWithLimit(PageRequestDTO pageRequestDTO, int limit);

}
