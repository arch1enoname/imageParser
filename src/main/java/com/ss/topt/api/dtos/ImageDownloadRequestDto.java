package com.ss.topt.api.dtos;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDownloadRequestDto {
    @Size(max = 2048)
    private String url;

    @Length(max = 32)
    private String folderName;
}
