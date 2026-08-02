package co.istad.chanda.ecommerce.features.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTagRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must be less than 50 characters")
        String name
) {
}
