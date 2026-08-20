package co.istad.chanda.ecommerce.features.tag;

import co.istad.chanda.ecommerce.features.tag.dto.TagRequest;
import co.istad.chanda.ecommerce.features.tag.dto.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse create(@RequestBody TagRequest tagRequest) {
        return tagService.createNew(tagRequest);
    }

    @PutMapping("/{id}")
    public TagResponse updateById(@PathVariable Integer id, @RequestBody TagRequest tagRequest) {
        return tagService.updateById(id, tagRequest);
    }

    @GetMapping("/{id}")
    public TagResponse findById(@PathVariable Integer id) {
        return tagService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        tagService.deleteById(id);
    }

    @GetMapping
    public Page<TagResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortById);
        return tagService.findAll(pageable);
    }

    @GetMapping("/search")
    public List<TagResponse> search(
            @RequestParam(required = false, defaultValue = "") String name) {
        return tagService.search(name);
    }

}
