package co.com.image.Imagemanager.service.impl;

import co.com.image.Imagemanager.dto.FileEntityDTO;
import co.com.image.Imagemanager.model.entity.FileEntity;
import co.com.image.Imagemanager.model.repository.FileRepository;
import co.com.image.Imagemanager.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileEntity store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = FileEntity.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        return fileRepository.save(fileEntity);
    }

    @Override
    public Optional<FileEntityDTO> getFile(UUID id) throws FileNotFoundException {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        return fileEntity.map(this::convertToFileDTO);
    }

    @Override
    public List<FileEntityDTO> getAllFiles() {
        return fileRepository.findAll().stream()
                .map(this::convertToFileDTO)
                .collect(Collectors.toList());
    }

    private FileEntityDTO convertToFileDTO(FileEntity fileEntity) {
        return FileEntityDTO.builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .type(fileEntity.getType())
                .data(fileEntity.getData())
                .build();
    }
}
