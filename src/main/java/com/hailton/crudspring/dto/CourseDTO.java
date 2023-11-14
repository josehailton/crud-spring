package com.hailton.crudspring.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CourseDTO(
        @JsonProperty("_id") Long id,
        @NotNull @NotBlank @Length(min = 3, max = 100) String name,
        @NotNull @Length(max = 20) String category,
        @NotNull @NotEmpty @Valid List<LessonDTO> lessons) {
}
