package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Cart;
import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.CartDTO;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

//    @Override
//    public List<CartDTO> list() {
//        List<Cart> carts = cartRepository.findAll();
//        List<CartDTO> cartDTOS = new ArrayList<>();
//        for(Cart cart:carts){
//            cartDTOS.add(modelMapper.map(cart, CartDTO.class));
//        }
//        return cartDTOS;
//    }

    @Override
    public CartDTO read(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//        Optional<Category> optionalCategory = categoryRepository.findByName(productDTO.getCategory());
//        if(optionalCategory.isEmpty()){
//            Category category = new Category();
//            category.setName(productDTO.getCategory());
//            product.setCategory(category);
//        }else{
//            product.setCategory(optionalCategory.get());
//        }
//
//        product.setRating(productDTO.getRating());
//        productRepository.save(product);
//        return product;
//
        return cartDTO;
    }

    @Override
    public Cart register(CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        return cartRepository.save(cart);
    }

    @Override
    public PageResponseDTO<CartDTO> list(PageRequestDTO pageRequestDTO) {
//        Page<ProductDTO> result = productRepository.findAll(PageRequest.of(pageRequestDTO.getPage(),pageRequestDTO.getSize()));
        Page<CartDTO> result = cartRepository.list(pageRequestDTO);

        return PageResponseDTO.<CartDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<CartDTO> listWithLimitCart(PageRequestDTO pageRequestDTO, int limit) {
        Pageable pageableWithLimit = pageRequestDTO.getPageableWithLimit(limit);
        Page<Cart> productPage = cartRepository.findAll(pageableWithLimit);
        List<CartDTO> dtoList = new ArrayList<>();
        for(Cart c:productPage.getContent()){
            CartDTO cartDTO = modelMapper.map(c,CartDTO.class);
            dtoList.add(cartDTO);
        }

        Page<CartDTO> result = new PageImpl<>(dtoList, pageableWithLimit, productPage.getTotalElements());

        return PageResponseDTO.<CartDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }
}
