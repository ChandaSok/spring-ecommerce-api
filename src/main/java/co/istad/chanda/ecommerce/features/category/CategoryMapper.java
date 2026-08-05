package co.istad.chanda.ecommerce.features.category;

import co.istad.chanda.ecommerce.features.category.dto.CategoryResponse;
import co.istad.chanda.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.chanda.ecommerce.features.category.dto.UpdateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    void toEntity(UpdateCategoryRequest dto, @MappingTarget Category category);

    // Source => parameter
    // Target => return value
    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Mapping(source = "name", target = "cateName")
    CategoryResponse mapCategoryToCategoryResponse(Category category);


}
