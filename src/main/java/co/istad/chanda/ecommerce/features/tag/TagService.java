package co.istad.chanda.ecommerce.features.tag;

import co.istad.chanda.ecommerce.features.tag.dto.TagRequest;
import co.istad.chanda.ecommerce.features.tag.dto.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    TagResponse createNew(TagRequest tagRequest);

    TagResponse updateById(Integer id, TagRequest tagRequest);

    TagResponse findById(Integer id);

    Page<TagResponse> findAll(Pageable pageable);

    List<TagResponse> search(String name);

    void deleteById(Integer id);
}
