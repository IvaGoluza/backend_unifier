package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.recension.RecensionRequestDTO;
import hr.fer.unifier.backend.service.RecensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recension")
public class RecensionResource {
    private final RecensionService recensionService;

    public ResponseEntity<Void> createRecension(@RequestBody RecensionRequestDTO recensionRequestDTO){
        recensionService.addRecension(recensionRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
