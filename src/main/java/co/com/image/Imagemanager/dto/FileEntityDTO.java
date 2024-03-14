package co.com.image.Imagemanager.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;
@Data
@Builder
public class FileEntityDTO implements Serializable {
    private UUID id;
    private String name;
    private String type;
    private byte[] data;
}
