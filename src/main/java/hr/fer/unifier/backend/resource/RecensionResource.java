package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.recension.RecensionRequestDTO;
import hr.fer.unifier.backend.service.RecensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/recension", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RecensionResource {
    private final RecensionService recensionService;

    @PostMapping
    public ResponseEntity<Void> createRecension(@RequestBody RecensionRequestDTO recensionRequestDTO){
        recensionService.addRecension(recensionRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
