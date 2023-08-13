package com.fakeapi.FakeStore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "CART_ITEM")
@Setter
@Getter
public class CartItem {

    @Id @Column(name = "CART_ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    CartItem에서 Cart를 참조할 일은 없을 것 같아, 해당 내용 주석처리-> 그런데 주석처리하면 매핑이 안됨.
    @ManyToOne
    @JoinColumn(name = "CART_ID") @JsonIgnore // Cart는 여러 CartItem을 참조하고, 각 CartItem도 Cart를 참조할 때 이 문제가 발생, cartItem을 통해 cart를 조회할 일은 없으므로 JsonIgnore
    private Cart cart;

//      상품이 판매 중단되면 그 정보는 상품(Product) 테이블에서 삭제되게 됩니다. 그런데 만약 CartItem이 Product를 직접 참조(FK, Foreign Key를 사용)하고 있었다면, 상품이 삭제될 때 해당 상품을 담고 있는 모든 장바구니(CartItem)의 정보도 같이 삭제되어야 하는 문제가 생깁니다. 이렇게 되면 장바구니 정보가 유실되거나 일관성이 깨질 수 있어요.
//      이런 문제를 방지하기 위해, CartItem은 Product를 직접 참조하지 않고 상품에 관한 필요한 정보만 복사하여 가지고 있습니다. 그래서 상품이 판매 중단되더라도, 장바구니에 담긴 상품 정보는 유지될 수 있게 되고, 사용자는 "제품이 없을 경우에는 판매하지 않는 상품이다"라는 정보를 받을 수 있게 됩니다. 이렇게 하면 시스템의 유연성과 안정성을 동시에 보장할 수 있게 됩니다.

    @Column(name = "PRODUCT_ID")
    private Long productId;
//    @Column(name = "PRODUCT_TITLE")
//    private String productTitle;
//    @Column(name = "PRODUCT_PRICE")
//    private Double productPrice;
//    @Column(name = "PRODUCT_DESC")
//    private String productDescription;
    @Column(name = "QUANTITY")
    private int quantity;
}

