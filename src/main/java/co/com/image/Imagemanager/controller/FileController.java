package co.com.image.Imagemanager.controller;

import co.com.image.Imagemanager.dto.FileEntityDTO;
import co.com.image.Imagemanager.model.entity.FileEntity;
import co.com.image.Imagemanager.response.ResponseMesssage;
import co.com.image.Imagemanager.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fileManager")
public class FileController {
    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMesssage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileEntity fileEntity = fileService.store(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMesssage("archivo subido exitosamente " + fileEntity.getId().toString()));
    }

    @GetMapping(value = "/files/{id}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) throws FileNotFoundException {
        FileEntityDTO fileEntity = fileService.getFile(id).orElseThrow(FileNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment : filename\"" + fileEntity.getName() + "\"")
                .body(fileEntity.getData());
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileEntityDTO>> getListFiles() {
        List<FileEntityDTO> files = fileService.getAllFiles();
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
}