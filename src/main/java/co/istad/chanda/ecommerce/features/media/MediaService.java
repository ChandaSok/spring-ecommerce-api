package co.istad.chanda.ecommerce.features.media;

import co.istad.chanda.ecommerce.features.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MediaService {

    /*
     * Upload multi file
     * */
    List<MediaResponse> upload(List<MultipartFile> files);

    /*
     * Upload single file
     * */
    MediaResponse upload(MultipartFile file);

    MediaResponse findByName(String name);

    // Get with pagination
    Page<MediaResponse> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Delete media by name
     */
    void deleteByName(String name);

    void draftByName(String name);

}
