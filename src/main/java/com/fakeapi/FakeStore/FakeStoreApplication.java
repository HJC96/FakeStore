package com.fakeapi.FakeStore;

import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FakeStoreApplication {
	public static void main(String[] args) {

		SpringApplication.run(FakeStoreApplication.class, args);
	}
}

