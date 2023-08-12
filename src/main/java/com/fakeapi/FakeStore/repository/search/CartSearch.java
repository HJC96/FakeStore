package com.fakeapi.FakeStore.repository.search;

import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface CartSearch {
    Page<CartDTO> list(PageRequestDTO pageRequestDTO);
}
