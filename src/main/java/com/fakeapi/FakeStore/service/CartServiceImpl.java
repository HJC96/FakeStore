package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Cart;
import com.fakeapi.FakeStore.domain.CartItem;
import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.*;
import com.fakeapi.FakeStore.repository.CartItemRepository;
import com.fakeapi.FakeStore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
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
//        Cart cart = modelMapper.map(cartDTO, Cart.class); //modelMapper 사용시, 사용 인스턴스가 detached 될 수 있음
        Cart cart = new Cart();
        cart.setDate(cartDTO.getDate());
        cart.setUserId(cartDTO.getUserId());
        List<CartItem> cartItemList = new ArrayList<>();

        for(CartItemDTO cartItemDTO : cartDTO.getProducts()){
            log.info(cartItemDTO.getProductId());
            log.info(cartItemDTO.getQuantity());
//            CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class); //modelMapper 사용시, 사용 인스턴스가 detached 될 수 있음
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cartItemDTO.getProductId());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setCart(cart);
//            cartItem.setCart(cart); // Set the cart for each cart item
            cartItemList.add(cartItem);
        }
        log.info("---------------------------0");
        cart.setProducts(cartItemList);
        log.info("---------------------------1");
        Cart c = cartRepository.save(cart); // This should save both Cart and CartItems because of CascadeType.ALL
        log.info("---------------------------2");
        return c;
    }

    @Override
    public CartDTO update(Long id, CartDTO cartDTO) {
        Optional<Cart> optionalCart = cartRepository.findById(id);

        if(optionalCart.isPresent()){
//            Cart cart = modelMapper.map(cartDTO,Cart.class);
            Cart cart = optionalCart.get();
            cart.setUserId(cartDTO.getUserId());
            cart.setDate(cartDTO.getDate());

            List<CartItem> cartItemList = new ArrayList<>();
            for(CartItemDTO cartItemDTO:cartDTO.getProducts()){
//                CartItem cartItem = modelMapper.map(cartItemDTO,CartItem.class);

                CartItem cartItem = new CartItem();
                cartItem.setProductId(cartItemDTO.getProductId());
                cartItem.setQuantity(cartItemDTO.getQuantity());
                cartItem.setCart(cart);
                cartItemList.add(cartItem);
            }
            List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartDTO.getId());
            for(CartItem c:cartItems)
                cartItemRepository.delete(c);
            cart.setProducts(cartItemList);

            cartRepository.save(cart);

            CartDTO updatedCartDTO = modelMapper.map(cart,CartDTO.class);
            updatedCartDTO.setProducts(cartDTO.getProducts());
            return updatedCartDTO;
        }else {
            throw new RuntimeException("Cart not found");
        }
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

    @Override
    public void delete(Long id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);


        if(!optionalCart.isPresent()){
            throw new RuntimeException("Cart not found");
        }

        for(CartItem cartItem: optionalCart.get().getProducts()){
            cartItemRepository.deleteById(cartItem.getId());
        }

        cartRepository.deleteById(id);
    }

    @Override
    public PageResponseDTO<CartDTO> listWithDateRange(PageRequestDTO pageRequestDTO, LocalDateTime startDate, LocalDateTime endDate) {
        Pageable pageable = pageRequestDTO.getPageable();
        Page<Cart> cart = cartRepository.findAllByDateBetween(startDate, endDate, pageable);

        List<CartDTO> cartDTOList = new ArrayList<>();
        for(Cart c:cart.getContent()) {
            CartDTO cartDTO = modelMapper.map(c, CartDTO.class);
            cartDTOList.add(cartDTO);
        }
        return PageResponseDTO.<CartDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(cartDTOList)
                .total((int)cart.getTotalElements())
                .build();
    }

    @Override
    public List<CartDTO> readUserCart(Long id) {
        List<Cart> cartList = cartRepository.findAllByUserId(id);
        List<CartDTO> updatedCartDTO = new ArrayList<>();
        for(Cart cart:cartList) {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            List<CartItemDTO> cartItemDTOList = new ArrayList<>();
            for (CartItem cartItem : cart.getProducts()) {
                CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class);
                cartItemDTOList.add(cartItemDTO);
            }
            cartDTO.setProducts(cartItemDTOList);
            updatedCartDTO.add(cartDTO);
        }

        return updatedCartDTO;
    }
}
