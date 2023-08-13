package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartItemRepositoryTest {

    @Autowired
    CartItemRepository cartItemRepository;

    private CartItem cartItem;

    @BeforeEach
    public void setUp() {
        cartItem = new CartItem();
        cartItem.setProductId(12345L); // 예시 상품 ID
        cartItem.setQuantity(2); // 예시 수량
    }

    @Test
    public void testSaveCartItem() {
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        assertNotNull(savedCartItem.getId()); // Verify if the cartItem was saved by checking its ID.
        assertEquals(cartItem.getProductId(), savedCartItem.getProductId()); // 상품 ID가 올바르게 저장되었는지 검증
        assertEquals(cartItem.getQuantity(), savedCartItem.getQuantity()); // 수량이 올바르게 저장되었는지 검증
    }
}
