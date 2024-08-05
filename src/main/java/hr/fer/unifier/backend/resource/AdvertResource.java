package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.advert.*;
import hr.fer.unifier.backend.service.AdvertService;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.sql.SQLException;


@RestController
@RequestMapping(value = "/advert", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AdvertResource {

    private final AdvertService advertService;

    @PostMapping
    public ResponseEntity<AdvertResponseDTO> saveAdvert(@RequestBody AdvertDTO advertDTO) {
        return ResponseEntity.ok(advertService.saveAdvert(advertDTO));
    }

    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdvertResponseDTO> saveAdvert(@RequestPart AdvertDTO advertDTO, @RequestPart MultipartFile file) {
        return ResponseEntity.ok(advertService.saveAdvert(advertDTO, file));
    }

    @PutMapping("/archive/{advertId}")
    public void changeDeleteStatus(@PathVariable Long advertId) {
        advertService.changeDeleteStatus(advertId);
    }

    @PutMapping("/undo-archive/{id}")
    public void undoArchive(@PathVariable Long id) {
        advertService.undoArchive(id);
    }

    @GetMapping("/my-adverts-info/{userId}")
    public ResponseEntity<UnifierPage<AdvertsInfoDTO>> getAdverts(@PathVariable Long userId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(advertService.getAdvertsInfo(userId, pageable));
    }

    @GetMapping("/{advertId}/my-advert/{userId}")
    public ResponseEntity<MyAdvertResponse> getAdvert(@PathVariable Long userId, @PathVariable Long advertId) {
        return ResponseEntity.ok(advertService.getAdvert(userId, advertId));
    }

    @GetMapping("/all-adverts/{userId}")
    public ResponseEntity<UnifierPage<AdvertResponseDTO>> getAdvertImage(
            @RequestParam(required = false, name = "grad") String city,
            @RequestParam(required = false, name = "kategorija") String category,
            @RequestParam(required = false, name = "vrstaPomoci") String helpType,
            @PathVariable Long userId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(advertService.getAllAdverts(city, category, helpType, userId, pageable));
    }


    @GetMapping("/image/{advertId}")
    public ResponseEntity<StreamingResponseBody> getAdvertImage(@PathVariable Long advertId) throws SQLException {
        return advertService.getAdvertImageDTO(advertId);
    }

    @PutMapping("/{advertId}/remove-helpers/{userId}")
    public ResponseEntity<Void> removeHelperVolunteer(@PathVariable Long advertId, @PathVariable Long userId) {
        advertService.removeHelperVolunteer(advertId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{advertId}/add-helpers/{userId}")
    public ResponseEntity<Void> addHelperVolunteer(@PathVariable Long advertId, @PathVariable Long userId) {
        advertService.addHelperVolunteer(advertId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-adverts/{userId}/titles")
    public ResponseEntity<UnifierPage<AdvertsTitlesDTO>> getRequestsTitles(@PathVariable Long userId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(advertService.getAdvertTitles(userId, pageable));
    }

}
