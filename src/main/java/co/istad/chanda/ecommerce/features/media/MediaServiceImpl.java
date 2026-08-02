package co.istad.chanda.ecommerce.features.media;

import co.istad.chanda.ecommerce.features.media.dto.MediaResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Sort;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.location}")
    private String mediaLocation;

    @Value("${media.client-path}")
    private String mediaClientPath;

    @Value("${media.base-uri}")
    private String mediaBaseUri;

    private final static String MB = "MB";

    // Upload single file
    public MediaResponse upload(MultipartFile file) {
        // Todo
        String name = UUID.randomUUID().toString();

        // Vital.png
        int lastIndexDot = file.getOriginalFilename().lastIndexOf('.');
        String extension = file.getOriginalFilename().substring(lastIndexDot);
        Path path = Paths.get(mediaLocation + name + "." + extension);
        log.info("Uploading media to location {}", path);

        // 2. Copy file
        try {
            Files.copy(file.getInputStream(), path);
        } catch (Exception e) {
            log.error("Failed to upload media", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media has been uploaded failed"
            );
        }

        // 3. Save into database
        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setSize((float) file.getSize());
        media.setMediaType(file.getContentType());
        media.setIsDraft(false);
        mediaRepository.save(media);

        // 1MB = 1_000_000B
        Float sizeAsMb = media.getSize() / 1_000_000;

//        return MediaResponse.builder()
//                .id(media.getId())
//                .name(media.getName())
//                .extension(media.getExtension())
//                .medialType(media.getMediaType())
//                .size(sizeAsMb)
//                .measurement(MB)
//                .uri(buildMediaUri(media))
//                .build();

        return buildResponseMedia(media);
    }

    private String buildMediaUri(Media media) {
        // http://localhost:1333/media/f35e5aec-d3f3-4cff-88f3-4dd918d941e0..jpg
        return mediaBaseUri + mediaClientPath + "/" + media.getName() +  "." + media.getExtension();
    }

    private MediaResponse buildResponseMedia(Media media) {
        // 1MB = 1_000_000B
        Float sizeAsMb = media.getSize() / 1_000_000;
        return MediaResponse.builder()
                .id(media.getId())
                .name(media.getName())
                .extension(media.getExtension())
                .medialType(media.getMediaType())
                .size(sizeAsMb)
                .measurement(MB)
                .uri(buildMediaUri(media))
                .build();
    }

    // Upload multi
    @Override
    public List<MediaResponse> upload(List<MultipartFile> files) {
        return files.stream().map(this::upload) // invoke upload (single)
                .toList();
    }

    @Override
    public MediaResponse findByName(String name) {
        Media media = mediaRepository.findByName(name)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Media has not been found"
                        )
                );
        return buildResponseMedia(media);
    }

    // Get with pagination
    @Transactional
    @Override
    public Page<MediaResponse> findAll(Integer pageNumber, Integer pageSize) {
        Sort sortByIdDesc = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByIdDesc);
        return mediaRepository
                .findAll(pageable)
                .map(this::buildResponseMedia);
    }

    @Override
    public void deleteByName(String name) {
        Media media = mediaRepository.findByName(name)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Media has not been found"
                        )
                );

        // Delete from database
        mediaRepository.delete(media);

        // Delete from file system
//        Path path = Paths.get(buildMediaPath(mediaLocation, media.getName(), media.getExtension()));
        Path path = Paths.get(mediaLocation + media.getName() + "." + media.getExtension());
        log.info("Deleting media from location {}", path);

        try {
            Files.delete(path);
        } catch (Exception e) {
            log.error("Failed to delete media", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong"
            );
        }
    }

    @Override
    public void draftByName(String name) {
        Media media = mediaRepository.findByName(name)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Media has not been found"
                        )
                );

        media.setIsDraft(Boolean.TRUE);
        mediaRepository.save(media);
    }

    private String buildMediaPath(String mediaLocation, String mediaName, String mediaExtension) {
        return mediaLocation + mediaName + "." + mediaExtension;
    }


}
