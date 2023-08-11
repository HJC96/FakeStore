package com.fakeapi.FakeStore.controller;

import com.fakeapi.FakeStore.domain.Cart;
import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private final CartService cartService;

//    @GetMapping
//    public List<CartDTO> cartList(){
//        return cartService.list();
//    }
//
    @GetMapping("/{id}")
    public CartDTO read(@PathVariable("id") Long id){
        log.info("read id: "+ id);
        return cartService.read(id);
    }

    @GetMapping(params = "limit")
    public PageResponseDTO<CartDTO> readLimit(
            @RequestParam(value="limit",required = false, defaultValue = "10") int limit,
            PageRequestDTO pageRequestDTO){
        if(limit > 0)
            return cartService.listWithLimitCart(pageRequestDTO, limit);
        else
            return cartService.list(pageRequestDTO);
    }

    @GetMapping
    public PageResponseDTO<CartDTO> list(PageRequestDTO pageRequestDTO) {
        return cartService.list(pageRequestDTO);
    }

    @GetMapping(params = "sort")
    public PageResponseDTO<CartDTO> listSortedCarts(
            @RequestParam(value="sort", defaultValue = "asc") String sort,
            PageRequestDTO pageRequestDTO) {

        pageRequestDTO.setSort(sort);

        return cartService.list(pageRequestDTO);
    }

    @PostMapping
    public Cart registerCart(@RequestBody @Valid CartDTO cartDTO){
        return cartService.register(cartDTO);
    }


}
