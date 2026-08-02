package co.istad.chanda.ecommerce.mapper;

import co.istad.chanda.ecommerce.dto.CreateProductRequest;
import co.istad.chanda.ecommerce.dto.PatchProductRequest;
import co.istad.chanda.ecommerce.dto.ProductResponse;
import co.istad.chanda.ecommerce.entity.Product;
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
