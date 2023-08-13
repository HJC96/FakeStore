package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class ProductServiceTests {

    @Autowired
    private ProductService productService;

    private Long sampleProductId;

    @BeforeEach
    public void setUp() {
        sampleProductId = 2L;
    }

//    //Github Action 환경에서는 실행 X, DB에 product가 없음
//    @Test
//    public void testService() {
//        ProductDTO retrievedProduct = productService.read(sampleProductId);
//
//        assertNotNull(retrievedProduct);
//        assertEquals(sampleProductId, retrievedProduct.getId());
//
//        log.info(retrievedProduct);
//    }
}
