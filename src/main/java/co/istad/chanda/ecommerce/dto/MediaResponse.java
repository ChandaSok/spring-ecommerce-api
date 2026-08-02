package co.istad.chanda.ecommerce.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        Integer id,
        String name,
        String extension,
        String medialType,
        Float size,
        String measurement, // MB
        String uri
) {
}
