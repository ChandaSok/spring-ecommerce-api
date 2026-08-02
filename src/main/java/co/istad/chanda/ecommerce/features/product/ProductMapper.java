package co.istad.chanda.ecommerce.features.product;

import co.istad.chanda.ecommerce.features.category.CategoryMapper;
import co.istad.chanda.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.chanda.ecommerce.features.product.dto.PatchProductRequest;
import co.istad.chanda.ecommerce.features.product.dto.ProductResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        CategoryMapper.class
})
public interface ProductMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(PatchProductRequest dto, @MappingTarget Product entity);

    ProductResponse toProductResponse(Product product);

    Product toEntity(CreateProductRequest createProductRequest);

}
