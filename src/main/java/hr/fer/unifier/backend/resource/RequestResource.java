package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.api.request.RequestsTitlesDTO;
import hr.fer.unifier.backend.service.RequestService;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RequestResource {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<RequestResponseDTO> saveRequest(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.saveRequest(requestDTO));
    }

    @PutMapping("/archive/{id}")
    public void archiveRequest(@PathVariable Long id) {
        requestService.archive(id);
    }

    @PutMapping("/undo-archive/{id}")
    public void undoArchive(@PathVariable Long id) {
        requestService.undoArchive(id);
    }

    @GetMapping("/{requestId}/my-request/{userId}")
    public ResponseEntity<RequestResponseDTO> getRequests(@PathVariable Long userId, @PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.getRequest(userId, requestId));
    }

    @GetMapping("/my-requests-info/{userId}")
    public ResponseEntity<UnifierPage<RequestsInfoDTO>> getRequests(@PathVariable Long userId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(requestService.getRequestInfo(userId, pageable));
    }

    @GetMapping("/my-requests/{userId}/titles")
    public ResponseEntity<UnifierPage<RequestsTitlesDTO>> getRequestsTitles(@PathVariable Long userId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(requestService.getRequestTitles(userId, pageable));
    }

    @GetMapping(value = {"/all-requests/{userId}"})
    public ResponseEntity<UnifierPage<RequestResponseDTO>> getAllRequests(
            @RequestParam(required = false, name = "grad") String city,
            @RequestParam(required = false, name = "kategorija") String category,
            @RequestParam(required = false, name = "vrstaPomoci") String helpType,
            Long userId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(requestService.getAllRequests(city, category, helpType, userId, pageable));
    }

}