package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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

  @GetMapping("/my-adverts/{userId}")
  public ResponseEntity<List<AdvertResponseDTO>> getAdverts(@PathVariable Long userId) {
    return ResponseEntity.ok(advertService.getAdverts(userId));
  }

  @GetMapping("/all-adverts")
  public ResponseEntity<Page<AdvertResponseDTO>> getAllAdverts(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(advertService.getAllAdverts(pageable));
  }

}
