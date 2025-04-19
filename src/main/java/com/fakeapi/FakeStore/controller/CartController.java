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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

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

    @PutMapping("/{id}") // PUT은 일반적으로 리소스 전체를 업데이트 하는데 사용
    public CartDTO updatePut(@PathVariable("id") Long id, @RequestBody @Valid CartDTO cartDTO){
        return cartService.update(id, cartDTO);
    }

    @PatchMapping("{id}") // PATCH는 일반적으로 리소스 일부를 업데이트 하는데 사용
    public CartDTO updatePatch(@PathVariable("id") Long id, @RequestBody @Valid CartDTO cartDTO){
        return cartService.update(id, cartDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cartService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping(params = {"startdate", "enddate"})
    public PageResponseDTO<CartDTO> listFilteredCarts(@RequestParam(value="startdate") String start,
            @RequestParam(value="enddate") String end,
            PageRequestDTO pageRequestDTO) {

        LocalDateTime startDate = LocalDate.parse(start).atStartOfDay(); // 여기서 변환
        LocalDateTime endDate = LocalDate.parse(end).plusDays(1).atStartOfDay(); // 다음 날의 시작 시간까지 포함


        return cartService.listWithDateRange(pageRequestDTO, startDate, endDate);
    }


    @GetMapping("/user/{id}")
    public List<CartDTO> readUserCart(@PathVariable("id") Long id){
        return cartService.readUserCart(id);
    }
}
