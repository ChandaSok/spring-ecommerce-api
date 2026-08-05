package co.istad.chanda.ecommerce.features.product.dto;

import co.istad.chanda.ecommerce.features.tag.dto.TagResponse;
import co.istad.chanda.ecommerce.features.category.dto.CategoryResponse;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        String code,
        String slug,
        String name,
        Integer qty,
        BigDecimal unitPrice,
        String thumbnail,
        String description,
        Boolean isAvailable,
        CategoryResponse category,
        List<TagResponse> tags
) {
}
