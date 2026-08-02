package co.istad.chanda.ecommerce.features.category;

import co.istad.chanda.ecommerce.features.category.dto.CategoryResponse;
import co.istad.chanda.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.chanda.ecommerce.features.category.dto.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    // Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }

    // Update
    @PutMapping("/{id}")
    public CategoryResponse updateById(@PathVariable Integer id,
                                       @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        return categoryService.updateById(id, updateCategoryRequest);
    }

    // Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createNew(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        return categoryService.createNew(createCategoryRequest);
    }

    // Get with pagination
    @GetMapping
    public Page<CategoryResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize
    ) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortById);
        return categoryService.findAll(pageable);
    }

}
