package com.ss.imageParser.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private final int FOLDER_NAME_MIN_CHARACTER = 2;
    private final int FOLDER_NAME_MAX_CHARACTER = 32;
    private final int URL_MAX_CHARACTER = 2048;

    @Size(max = URL_MAX_CHARACTER, message = "Url не должен превышать " + URL_MAX_CHARACTER + " символов")
    @NotBlank(message = "Url не должен быть пустым")
    @NotNull(message = "Url не должен быть null")
    private String url;

    @Length(min = FOLDER_NAME_MIN_CHARACTER, max = FOLDER_NAME_MAX_CHARACTER, message = "Имя папки должно быть длиной от " + FOLDER_NAME_MIN_CHARACTER
            +  " до " + FOLDER_NAME_MAX_CHARACTER + " символов")
    @NotBlank(message = "Имя папки не должно быть пустым")
    @NotNull(message = "Имя папки не должно быть null")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Имя папки может содержать только буквы английского алфавита")
    private String folderName;
}
