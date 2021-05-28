package org.khandora.mit.controller;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.khandora.mit.dto.FileDto;
import org.khandora.mit.masssage.ResponseMessage;
import org.khandora.mit.model.FileDB;
import org.khandora.mit.repository.FileDBRepository;
import org.khandora.mit.services.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/file")
public class FileController {

    private final FileStorageService storageService;
    private final FileDBRepository fileDBRepository;

    @PostMapping("/{taskId}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable (value = "taskId") Long taskId,
                                                      @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file, taskId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Transactional
    @GetMapping("/{id}/all")
    public ResponseEntity<List<FileDto>> getListFiles(@PathVariable (value = "id") Long id) {
        List<FileDto> files = storageService.getAllFiles(id).map(dbFile -> new FileDto(
                dbFile.getId(),
                dbFile.getName(),
                dbFile.getType(),
                dbFile.getData().length)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable (value = "id") String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .body(fileDB.getData());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteFile(@PathVariable (value = "id") String id) {
        FileDB fileDB = fileDBRepository.findById(id).orElseThrow(RuntimeException::new);
        fileDBRepository.delete(fileDB);

        return ResponseEntity.ok().build();
    }
}
