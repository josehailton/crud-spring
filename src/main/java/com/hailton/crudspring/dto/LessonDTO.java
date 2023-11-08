package com.hailton.crudspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LessonDTO(
        Long id,
        @NotNull @NotBlank @Length(min = 3, max = 100) String name,
        @NotNull @Length(min = 10, max = 11) String youtubeUrl) {
}
