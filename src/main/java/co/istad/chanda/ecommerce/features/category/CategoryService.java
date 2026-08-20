package co.istad.chanda.ecommerce.features.category;

import co.istad.chanda.ecommerce.features.category.dto.CategoryResponse;
import co.istad.chanda.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.chanda.ecommerce.features.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    // Find by id
    CategoryResponse findById(Integer id);

    // Delete
    void deleteById(Integer id);

    // Update
    CategoryResponse updateById(Integer id, UpdateCategoryRequest updateCategoryRequest);


    /**
     * បង្កើត category ថ្មី
     *
     * @param createCategoryRequest ព័ត៌មានសម្រាប់បង្កើត category ថ្មី
     * @return CategoryResponse
     */
    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);


    /**
     * ទាញព័ត៌មាន category ទាំងអស់
     *
     * @return សំណុំនៃ CategoryResponse
     */
    Page<CategoryResponse> findAll(Pageable pageable);

    /**
     * ស្វែងរក category តាម keyword
     */
    List<CategoryResponse> search(String name);

}
