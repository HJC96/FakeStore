## 프로젝트 개요
Java와 SpringBoot를 이용하여 가상의 온라인 상점 API를 구현했습니다. 이 프로젝트는 FakeStoreAPI를 기반한 것으로, 이는 실제 상점의 다양한 기능을 제공하는 JavaScript 기반 오픈소스 API 서버입니다. 상품 조회, 장바구니 관리, 사용자 인증 등의 기능을 포함하고 있습니다.


<!--
# API 서버를 만들면서 기록한 내용들 입니다.
-->
## 개발환경
- 개발언어: 자바
- IDE: IntellJ(Community Edition)
- 프로젝트 SDK: JDK 11 -> JDK 17
- Spring Boot: 2.7.13 -> 3.1.2
- 의존성 관리 툴: Maven

<!--
1. 환경 세팅(기본 프로젝트 셋팅시)

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.fakeapi.example</groupId>
    <artifactId>FakeAPI</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```
-->

## Architecture
<img width="806" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/26f21b57-defc-4342-a2d2-aee86d50f8a8">



## ERD 설계

<img width="600" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/11abc907-c7f9-44a0-b7cc-158e356435a4">

<img width="600" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/3347816a-6e3d-41c4-9f75-4e0e7748e142">

<img width="600" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/5db3c664-ceed-40bf-bd74-4a9216d89374">

## API overview
<!-- <img width="644" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/ad9c3c76-7219-46ee-a33d-dcfecbc2b008"> -->
<img width="806" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/93387252-0b42-4b3b-a672-8d90895c818c">

## Sample Data
다음의 데이터를 추가해주세요.
- Products https://fakestoreapi.com/products
- Carts https://fakestoreapi.com/carts


## Example Code

### 회원가입
~~~terminal
curl --location --request POST 'localhost:8080/members/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
"name":"이름",
"email":"이메일",
"password":"8자이상,대소문자특수문자섞은암호",
"birthYear":"년도",
"birthMonth":"월",
"birthDay":"일",
"gender": "M or F"
}'
~~~
### 로그인
~~~terminal
curl --location --request POST 'localhost:8080/members/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"email":"이메일",
"password":"암호"
}'
~~~
### 회원정보 읽어오기
~~~terminal
curl  --request GET 'http://localhost:8080/members/info' \
--header 'Authorization: Bearer 엑세스키' \
--header 'Content-Type: application/json'
~~~
### 로그아웃
~~~terminal
curl --location --request DELETE 'http://localhost:8080/members/logout' \
--header 'Authorization: Bearer accessToken' \
--header 'Content-Type: application/json' \
--data '{
    "refreshToken" : "리프래시토큰"
}'
~~~
### 리프레시 토큰
~~~terminal
curl --location 'http://localhost:8080/members/refreshToken' \
--header 'Content-Type: application/json' \
--data '{
"refreshToken" : "리프래시토큰"
}'
~~~

<!--

1. domain 작성

```java
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;
    private String description;

    private String category;

//    private ImageSet image;
//  rating -> json 객체인데 어떻게 표현해야할지 모르겠음...{}
}
```

1. repo 작성
    1. jpa연동
    
    ```java
    public interface ProductRepository extends JpaRepository<Product, Long> {
    }
    ```
    
    1. db테이블 작성
    
    ```sql
    create table fakestore(
    id BIGINT auto_increment primary key,
    title varchar(100) not null,
    price DOUBLE
    );
    ```
    
    <img width="513" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/96b83285-d4e8-47b5-af4a-30f541183e9b">

    
    *USER, HOST 확인
    
    ```bash
    SELECT User, Host From mysql.user;
    ```
    
    <img width="539" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/e94b23c5-4ad7-4197-be29-fc139a6305ec">

    
    mysql - user 생성(**어떤 특정 디비에 들어가지 말고 none인 상태에서 할것!**!!)
    
    ```bash
    CREATE USER 'newuser'@'%' IDENTIFIED BY 'password';
    ```
    
    <img width="543" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/e7435b92-e58f-414e-8324-b6abd9fb15fc">

    
    권한 부여
    
    ```bash
    GRANT ALL PRIVILEGES ON fakestore.* TO 'storeuser'@'localhost';
    ```
    
    <img width="534" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/af5021e1-7e82-41c8-9111-aa88b526099b">

    
    1. 테스트코드
    
    - 오류 발생
        - 테스트 코드 에러 db에 JPA로 쏴주는게 안됨…/
        - 네이버 블로그에 트러블 슈팅 작성 완료
    
2. service 작성

```java
package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Product;

public interface FakeStoreService {
    Product read(Long id);
}
```

```java
package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FakeStoreServiceImpl implements FakeStoreService{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public Product read(Long id) {
        Optional<Product> result = productRepository.findById(id);
        Product product = result.orElseThrow();
        return modelMapper.map(todo, TodoDTO.class);
    }
}
```

- modelMapper 등록 필요
    - modelMapper란?
        - 엔티티 객체 ↔ DTO 도와주는 라이브러리

**pom.xml**

```java
<dependency>
			<groupId>org.modelmapper</groupId>
			<artifactId>modelmapper</artifactId>
			<version>3.0.0</version>
</dependency>
```

**config 디렉토리에 RootConfig.java 추가**

```java
package com.fakeapi.FakeStore.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
```

**DTO/ProductDTO.java 작성**

```java
package com.fakeapi.FakeStore.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor

@Data
//@Getter
//@Setter
//@ToString
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private String title;
}
```

ProductService 작성

```java
package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.dto.ProductDTO;

public interface ProductService {
    ProductDTO read(Long id);
}
```

ProductServiceImpl 작성

```java
package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Product;
import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

//    public FakeStoreServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//    }

    @Override
    public ProductDTO read(Long id) {
        Optional<Product> result = productRepository.findById(id);
        Product product = result.orElseThrow();
        return modelMapper.map(product, ProductDTO.class);
    }
}
```

Service TEST코드 작성

```java
package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class ProductServiceTests {
    @Autowired
    ProductServiceImpl productServiceImpl;
    @Test
    public void ProductServiceTest(){
        ProductDTO productDTO =productServiceImpl.read(2L);
        log.info(productDTO);

    }
}
```

1. controller 작성

ProductController

```java
package com.fakeapi.FakeStore.controller;

import com.fakeapi.FakeStore.dto.ProductDTO;
import com.fakeapi.FakeStore.service.ProductService;
import com.fakeapi.FakeStore.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/products") 
@RequiredArgsConstructor // final or @NotNull이 붙은 필드의 생성자를 생성해주는 어노테이션
public class ProductController {

    private final ProductService productService;
    @GetMapping("/{id}")
    public ProductDTO read(@PathVariable("id") Long id){
        log.info("read id: "+ id);
        return productService.read(id);
    }

}
```

- 테스트

<img width="561" alt="image" src="https://github.com/HJC96/FakeStore/assets/87226129/6381bcee-c23d-4aee-a571-db70a844b708">

-->




