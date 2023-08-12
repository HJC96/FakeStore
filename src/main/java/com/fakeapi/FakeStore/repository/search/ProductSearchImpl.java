package com.fakeapi.FakeStore.repository.search;




import com.fakeapi.FakeStore.domain.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;

import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.fakeapi.FakeStore.dto.PageRequestDTO;
import com.fakeapi.FakeStore.dto.ProductDTO;

import jakarta.persistence.*;
import java.util.List;

public class ProductSearchImpl extends ßßßQuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<ProductDTO> list(PageRequestDTO pageRequestDTO) {

        QProduct product = QProduct.product;
        QCategory category = QCategory.category; // Category에 대한 Querydsl 객체 생성

        JPQLQuery<Product> query = from(product).leftJoin(product.category, category);
//
//        if(pageRequestDTO.getFrom() != null && pageRequestDTO.getTo() != null){
//
//            BooleanBuilder fromToBuilder = new BooleanBuilder();
//            fromToBuilder.and(product.dueDate.goe(pageRequestDTO.getFrom()));
//            fromToBuilder.and(product.dueDate.loe(pageRequestDTO.getTo()));
//            query.where(fromToBuilder);
//        }
//
//        if(pageRequestDTO.getCompleted() != null){
//            query.where(product.complete.eq(pageRequestDTO.getCompleted()));
//        }
//
//        if(pageRequestDTO.getKeyword() != null){
//            query.where(product.title.contains(pageRequestDTO.getKeyword()));
//        }
//
//        this.getQuerydsl().applyPagination(pageRequestDTO.getPageable("id"), query);
//
//        JPQLQuery<Product> dtoQuery = query.select(Projections.bean(TodoDTO.class,
//                product.tno,
//                product.title,
//                product.dueDate,
//                product.complete,
//                product.writer
//        ));
        //paging

        this.getQuerydsl().applyPagination(pageRequestDTO.getPageable("id"), query);

        JPQLQuery<ProductDTO> dtoQuery = query.select(Projections.bean(ProductDTO.class,
                product.id,
                product.title,
                product.price,
                product.description,
//                product.category.name
                category.name,
                product.image,
                product.rating
        ));


        List<ProductDTO> list = dtoQuery.fetch();
//        for(ProductDTO productDTO:list) System.out.println(productDTO.getCategory());
        long count = dtoQuery.fetchCount();
        return new PageImpl<>(list, pageRequestDTO.getPageable("id"), count);
    }
}

