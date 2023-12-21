package com.fakeapi.FakeStore.dto;


import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type; // 검색의 종류 t,c, w, tc,tw, twc

    private String keyword;

    private String sort;

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String... props) {
        if ("desc".equalsIgnoreCase(sort)) {
            return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
        }
        return PageRequest.of(this.page -1, this.size, Sort.by(props).ascending());
    }

    public Pageable getPageableWithLimit(int limit, String... props) {
        if ("desc".equalsIgnoreCase(sort)) {
            return PageRequest.of(this.page - 1, limit, Sort.by(props).descending());
        }
        return PageRequest.of(this.page - 1, limit, Sort.by(props).ascending());
    }


    private String link;

    public String getLink() {

        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if(type != null && type.length() > 0){
                builder.append("&type=" + type);
            }
            if(keyword != null){
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }

        return link;
    }





}