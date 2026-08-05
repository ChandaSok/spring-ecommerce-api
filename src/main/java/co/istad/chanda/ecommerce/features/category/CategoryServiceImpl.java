package co.istad.chanda.ecommerce.features.category;

import co.istad.chanda.ecommerce.features.category.dto.CategoryResponse;
import co.istad.chanda.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.chanda.ecommerce.features.category.dto.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse findById(Integer id) {
        // Validate category ID
        return categoryRepository.findById(id)
                .map(categoryMapper::mapCategoryToCategoryResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category has not been found"
                        )
                );
    }

    @Override
    public void deleteById(Integer id) {
        // Validate category ID
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category has not been found"
                        )
                );
        categoryRepository.delete(category);
    }

    // Update
    @Override
    public CategoryResponse updateById(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        // Validate category ID
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category has not been found"
                        )
                );

        // Validate category name (prevent duplicated name)
        if (categoryRepository.existsByName(updateCategoryRequest.name())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category already exists");
        }

        categoryMapper.toEntity(updateCategoryRequest, category);
        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    // Create
    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {
        // 1. Validate all information form DTO
        // Validate category name (unique)
        Optional<Category> category = categoryRepository.findByName(createCategoryRequest.name());

        if (category.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category name already exists"
            );
        }

        Category newCategory = categoryMapper
                .mapCreateCategoryRequestToCategory(createCategoryRequest);
        newCategory.setIsDeleted(false);

        // Validate parent category id
        if (createCategoryRequest.parentCategoryId() != null) {
            Category parentCategory = categoryRepository
                    .findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent category not found")
                            );
            newCategory.setParentCategory(parentCategory);
        }

        newCategory = categoryRepository.save(newCategory);

        return categoryMapper.mapCategoryToCategoryResponse(newCategory);
    }

    // Get with pagination
    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        // Get data from database
        Page<Category> categories = categoryRepository.findAll(pageable);

        // បំលែង entity ទៅជា DTO
        return categories.map(categoryMapper::mapCategoryToCategoryResponse);
    }

}
