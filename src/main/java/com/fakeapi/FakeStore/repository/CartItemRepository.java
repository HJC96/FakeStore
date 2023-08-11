package com.fakeapi.FakeStore.repository;//package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByCartId(Long cartId);

}
