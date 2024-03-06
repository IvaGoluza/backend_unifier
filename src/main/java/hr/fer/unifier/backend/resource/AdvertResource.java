package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.api.advert.AdvertsInfoDTO;
import hr.fer.unifier.backend.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    return ResponseEntity.ok(advertService.saveAdvert(advertDTO,file));
  }

  @PutMapping("/change-delete-status/{advertId}")
  public void changeDeleteStatus(@PathVariable Long advertId) {
    advertService.changeDeleteStatus(advertId);
  }

  @GetMapping("/my-adverts-info/{userId}")
  public ResponseEntity<Page<AdvertsInfoDTO>> getAdverts(@PathVariable Long userId, @ParameterObject Pageable pageable) {
    return ResponseEntity.ok(advertService.getAdvertsInfo(userId, pageable));
  }

  @GetMapping("/{advertId}/my-advert/{userId}")
  public ResponseEntity<AdvertResponseDTO> getAdvert(@PathVariable Long userId, @PathVariable Long advertId) {
    return ResponseEntity.ok(advertService.getAdvert(userId,advertId));
  }

  @GetMapping("/all-adverts")
  public ResponseEntity<Page<AdvertResponseDTO>> getAllAdverts(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(advertService.getAllAdverts(pageable));
  }

}
