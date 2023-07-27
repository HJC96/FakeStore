//package com.fakeapi.FakeStore.repository;
//
//import com.fakeapi.FakeStore.domain.Cart;
//import com.fakeapi.FakeStore.domain.CartItem;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.persistence.*;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CartItemRepositoryTest {
//    @Autowired
//    CartItemRepository cartItemRepository;
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "CART_ID")
//    private Cart cart;
//    @JoinColumn(name = "PRODUCT_ID")
//    private Long productId;
//    @JoinColumn(name = "PRODUCT_TITLE")
//    private String productTitle;
//    @JoinColumn(name = "PRODUCT_PRICE")
//    private Double productPrice;
//    @JoinColumn(name = "PRODUCT_DESC")
//    private String productDescription;
//    @JoinColumn(name = "QUANTITY")
//    private int quantity;
//
//    @Test
//    public void TST(){
//        CartItem cartItem = new CartItem();
//
//
//        cartItemRepository.save(cartItem);
//    }
//
//}