package hr.fer.unifier.backend.controller;

import hr.fer.unifier.backend.model.DTO.AdvertDTO;
import hr.fer.unifier.backend.model.DTO.AdvertResponseDTO;
import hr.fer.unifier.backend.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class AdvertController {

  private final AdvertService advertService;

  @Autowired
  public AdvertController(AdvertService advertService) {
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
