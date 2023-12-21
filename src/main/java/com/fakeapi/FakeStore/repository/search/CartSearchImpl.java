package com.fakeapi.FakeStore.repository.search;

import com.fakeapi.FakeStore.domain.*;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.CartItemDTO;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CartSearchImpl extends QuerydslRepositorySupport implements CartSearch{

    public CartSearchImpl() {
        super(Cart.class);
    }

@Override
public Page<CartDTO> list(PageRequestDTO pageRequestDTO) {
    QCart cart = QCart.cart;
    QCartItem cartItem = QCartItem.cartItem;

    // Cart 정보와 연관된 CartItem 정보를 JOIN FETCH 방식으로 같이 조회
    JPQLQuery<Cart> cartQuery = from(cart)
            .leftJoin(cart.products, cartItem).fetchJoin();  // JOIN FETCH

    // 페이징 적용
    this.getQuerydsl().applyPagination(pageRequestDTO.getPageable("id"), cartQuery);
    List<Cart> carts = cartQuery.fetch();

    List<CartDTO> list = new ArrayList<>();

    for (Cart c : carts) {
        CartDTO dto = new CartDTO();
        dto.setId(c.getId());
        dto.setUserId(c.getUserId());
        dto.setDate(c.getDate());

        // JOIN FETCH로 CartItem 정보를 가져왔으므로 별도의 쿼리 없이 변환
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem item : c.getProducts()) {
            CartItemDTO cartItemDTO = new CartItemDTO(item.getProductId(), item.getQuantity());

            cartItemDTOs.add(cartItemDTO);
        }

        dto.setProducts(cartItemDTOs);
        list.add(dto);
    }

    long count = cartQuery.fetchCount();

    return new PageImpl<>(list, pageRequestDTO.getPageable("id"), count);
}


}
