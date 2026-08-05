package co.istad.chanda.ecommerce.features.product;

import co.istad.chanda.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.chanda.ecommerce.features.product.dto.PatchProductRequest;
import co.istad.chanda.ecommerce.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    /**
     * Patch product by ID
     */
    ProductResponse patchById(Integer id, PatchProductRequest patchProductRequest);


    /**
     * Find products by pagination
     */
    Page<ProductResponse> findAll(int pageNumber, int pageSize);


    /**
     * Create a new product
     */
    void createNew(CreateProductRequest createProductRequest);

    /**
     * Search products by name with pagination
     */
    Page<ProductResponse> search(String name, int pageNumber, int pageSize);

}
