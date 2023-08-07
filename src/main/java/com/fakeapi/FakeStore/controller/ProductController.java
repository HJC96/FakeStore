package com.fakeapi.FakeStore.controller;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.PageResponseDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.service.CategoryService;
import com.fakeapi.FakeStore.service.ProductService;
import com.fakeapi.FakeStore.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@RequestMapping("/products")
@RequiredArgsConstructor // final or @NotNull이 붙은 필드의 생성자를 생성해주는 어노테이션
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    //Product

    @GetMapping("/{id}")
    public ProductDTO read(@PathVariable("id") Long id){
        log.info("read id: "+ id);
        return productService.read(id);
    }

    @GetMapping(params = "limit")
    public PageResponseDTO<ProductDTO> readLimit(
            @RequestParam(value="limit",required = false, defaultValue = "10") int limit,
            PageRequestDTO pageRequestDTO){
        if(limit > 0)
            return productService.listWithLimit(pageRequestDTO, limit);
        else
            return productService.list(pageRequestDTO);
    }

    @GetMapping
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        return productService.list(pageRequestDTO);
    }

    @GetMapping(params = "sort")
    public PageResponseDTO<ProductDTO> listSortedProducts(
            @RequestParam(value="sort", defaultValue = "asc") String sort,
            PageRequestDTO pageRequestDTO) {

        pageRequestDTO.setSort(sort);

        return productService.list(pageRequestDTO);
    }

    @PostMapping
    public Product registerProduct(@RequestBody @Valid ProductDTO productDTO){
        return productService.register(productDTO);
    }

    // Category
    @GetMapping("/categories")
    public List<Category> category_list( ) {
        return categoryService.list();
    }

//    @GetMapping("/category/{id}")
//    public Optional<Category> readId_category(@PathVariable("id") Long id) {
//        return categoryService.read(id);
//    }


    @GetMapping("/category/{categoryName}")
    public List<ProductDTO> readName_category(@PathVariable("categoryName") String categoryName){
        return productService.listByCategoryName(categoryName);
    }




}