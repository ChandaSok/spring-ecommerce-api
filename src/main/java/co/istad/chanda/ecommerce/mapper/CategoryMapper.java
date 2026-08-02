package co.istad.chanda.ecommerce.mapper;

import co.istad.chanda.ecommerce.dto.CategoryResponse;
import co.istad.chanda.ecommerce.dto.CreateCategoryRequest;
import co.istad.chanda.ecommerce.dto.UpdateCategoryRequest;
import co.istad.chanda.ecommerce.entity.Category;
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
