package co.com.image.Imagemanager.service;

import co.com.image.Imagemanager.dto.FileEntityDTO;
import co.com.image.Imagemanager.model.entity.FileEntity;
import co.com.image.Imagemanager.response.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFileService {
    FileEntity store(MultipartFile file) throws IOException;
    Optional<FileEntityDTO> getFile(UUID id) throws FileNotFoundException;
    List<FileEntityDTO> getAllFiles();
}

