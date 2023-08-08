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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PutMapping("/{id}") // PUT은 일반적으로 리소스 전체를 업데이트 하는데 사용
    public ProductDTO updatePut(@PathVariable("id") Long id, @RequestBody @Valid ProductDTO productDTO){
        return productService.update(id, productDTO);
    }

    @PatchMapping("{id}") // PATCH는 일반적으로 리소스 일부를 업데이트 하는데 사용
    public ProductDTO updatePatch(@PathVariable("id") Long id, @RequestBody @Valid ProductDTO productDTO){
        return productService.update(id, productDTO);
    }

    //DELETE 시 CATEGORY_ID 테이블때문에 삭제 안되는 이슈
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
    public List<Category> categoryList( ) {
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