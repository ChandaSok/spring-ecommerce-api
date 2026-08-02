package co.istad.chanda.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String cateName,
        String description,
        String icon,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        CategoryResponse parentCategory
) {
}
