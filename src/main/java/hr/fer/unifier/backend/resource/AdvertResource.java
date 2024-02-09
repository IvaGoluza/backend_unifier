package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class AdvertResource {

  private final AdvertService advertService;

  @Autowired
  public AdvertResource(AdvertService advertService) {
    this.advertService = advertService;
  }

  @PostMapping("/my-adverts")
  public ResponseEntity<AdvertResponseDTO> saveAdvert(@RequestBody AdvertDTO advertDTO) {
    return ResponseEntity.ok(advertService.saveAdvert(advertDTO));
  }

  @PutMapping("/my-adverts/{advertId}")
  public void deleteAdvert(@PathVariable Long advertId) {
    advertService.deleteAdvert(advertId);
  }

  @GetMapping("/my-adverts/{userId}")
  public ResponseEntity<List<AdvertResponseDTO>> getAdverts(@PathVariable Long userId) {
    return ResponseEntity.ok(advertService.getAdverts(userId));
  }

  @GetMapping("/volunteer-adverts")
  public ResponseEntity<List<AdvertResponseDTO>> getAllAdverts() {
    return ResponseEntity.ok(advertService.getAllAdverts());
  }

}
