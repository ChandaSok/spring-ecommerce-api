package co.istad.chanda.ecommerce.features.tag;

import co.istad.chanda.ecommerce.features.tag.dto.CreateTagRequest;
import co.istad.chanda.ecommerce.features.tag.dto.TagResponse;
import co.istad.chanda.ecommerce.features.tag.dto.UpdateTagRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    TagResponse findById(Integer id);

    void deleteById(Integer id);

    TagResponse updateById(Integer id, UpdateTagRequest updateTagRequest);

    TagResponse createNew(CreateTagRequest createTagRequest);

    Page<TagResponse> findAll(Pageable pageable);

}
