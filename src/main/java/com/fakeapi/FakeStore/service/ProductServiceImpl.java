package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Product;

import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.repository.CategoryRepository;
import com.fakeapi.FakeStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor @Log4j2
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

        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product p:product){
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
    public PageResponseDTO<ProductDTO> listWithLimitProduct(PageRequestDTO pageRequestDTO, int limit) {
        // pageRequestDTO.setSize(limit);
        Pageable pageableWithLimit = pageRequestDTO.getPageableWithLimit(limit);
        Page<Product> productPage = productRepository.findAll(pageableWithLimit);
        List<ProductDTO> dtoList = new ArrayList<>();
        for(Product p:productPage.getContent()){
            ProductDTO productDTO = modelMapper.map(p,ProductDTO.class);
            dtoList.add(productDTO);
        }

        Page<ProductDTO> result = new PageImpl<>(dtoList, pageableWithLimit, productPage.getTotalElements());

        return PageResponseDTO.<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Category> optionalCategory = categoryRepository.findByName(productDTO.getCategory());

        if(optionalProduct.isPresent()){
            // modelMapper가 예상대로 동작하지 않음. 수동 매핑
            // modelMapper.map(productDTO, Product.class);
            Product product = optionalProduct.get();
            product.setTitle(productDTO.getTitle());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setImage(productDTO.getimage());
            product.setCategory(optionalCategory.get());
            product.setRating(productDTO.getRating());
            productRepository.save(product);

            ProductDTO updatedProductDTO = modelMapper.map(product,ProductDTO.class);
            updatedProductDTO.setCategory(optionalCategory.get().getName());
            return updatedProductDTO;
        }else{
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent()){
            throw new RuntimeException("Product not found");
        }
        // Product와 연결된 Category에 대한 외래키 제약 조건이 있음. 이것 때문에 삭제가 되지 않아 추가한 로직.
        // -> product의 category가 null값을 허용하도록 바꾸기보다는, cascade = {CascadeType.PERSIST, CascadeType.MERGE} 으로 변경
//        optionalProduct.get().setCategory(null);
//        productRepository.save(optionalProduct.get());
        productRepository.deleteById(id);
    }
}
