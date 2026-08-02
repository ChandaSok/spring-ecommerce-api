package co.istad.chanda.ecommerce.features.tag;

import co.istad.chanda.ecommerce.features.tag.dto.CreateTagRequest;
import co.istad.chanda.ecommerce.features.tag.dto.TagResponse;
import co.istad.chanda.ecommerce.features.tag.dto.UpdateTagRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag fromCreateRequest(CreateTagRequest request);

    void fromUpdateRequest(UpdateTagRequest request, @MappingTarget Tag tag);

    TagResponse toResponse(Tag tag);

}
