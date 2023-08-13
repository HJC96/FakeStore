package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.domain.Rating;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category createSampleCategory() {
        Category category = new Category();
        category.setName("men's clothing");
        return category;
    }

    private Rating createSampleRating() {
        Rating rating = new Rating();
        rating.setRate(3.9);
        rating.setCount(120);
        return rating;
    }

    private Product createSampleProduct(Category category, Rating rating) {
        Product product = new Product();
        product.setTitle("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops");
        product.setPrice(109.0);
        product.setDescription("Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday");
        product.setImage("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");
        product.setRating(rating);
        product.setCategory(category);
        return product;
    }

    @Test
    public void testProductRepository() {
        Category category = createSampleCategory();
        Rating rating = createSampleRating();
        Product product = createSampleProduct(category, rating);

        Product savedProduct = productRepository.save(product);

        // Flush changes and re-load savedProduct
        productRepository.flush();
        savedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

//        // Change the title of the product
//        product.setTitle("test");

        // Assertions
        assertNotNull(savedProduct.getId());
        assertEquals(product.getTitle(), savedProduct.getTitle());
        assertEquals(product.getPrice(), savedProduct.getPrice());

        // Log the saved Product's ID
        log.info("Saved product id: " + savedProduct.getId());
    }

}
