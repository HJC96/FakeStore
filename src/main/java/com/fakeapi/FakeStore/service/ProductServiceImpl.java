package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Product;

import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.repository.CategoryRepository;
import com.fakeapi.FakeStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

//    public FakeStoreServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//    }

    @Override
    public Product register(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Optional<Category> optionalCategory = categoryRepository.findByName(productDTO.getCategory());
        if(optionalCategory.isEmpty()){
            Category category = new Category();
            category.setName(productDTO.getCategory());
            product.setCategory(category);
        }else{
            product.setCategory(optionalCategory.get());
        }

        product.setRating(productDTO.getRating());
        productRepository.save(product);
        return product;
    }

    @Override
    public ProductDTO read(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        ProductDTO productDTO = modelMapper.map(product,ProductDTO.class);
        productDTO.setCategory(product.getCategory().getName());

        return productDTO;
    }
    @Override
    public List<ProductDTO> listByCategoryName(String name){
        List<Product> product = productRepository.findAllByCategoryName(name);
        System.out.println("---------------");

        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product p:product){
            System.out.println("---------------");
            System.out.println(p.getCategory());
            ProductDTO productDTO = modelMapper.map(p,ProductDTO.class);
            productDTO.setCategory(p.getCategory().getName());
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }



    @Override
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
//        Page<ProductDTO> result = productRepository.findAll(PageRequest.of(pageRequestDTO.getPage(),pageRequestDTO.getSize()));
        Page<ProductDTO> result = productRepository.list(pageRequestDTO);

        return PageResponseDTO.<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ProductDTO> list_limit(PageRequestDTO pageRequestDTO, int limit) {
        Page<ProductDTO> result = productRepository.list(pageRequestDTO);

        return PageResponseDTO.<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
//                .total((int)result.getTotalElements())
                .total(limit)
                .build();
    }

}
