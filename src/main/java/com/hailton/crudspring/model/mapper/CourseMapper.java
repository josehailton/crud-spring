package com.hailton.crudspring.model.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.hailton.crudspring.dto.CourseDTO;
import com.hailton.crudspring.dto.LessonDTO;
import com.hailton.crudspring.enums.Category;
import com.hailton.crudspring.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        List<LessonDTO> lessons = course.getLessons().stream()
                .map(lesson -> new LessonDTO(
                        lesson.getId(),
                        lesson.getName(),
                        lesson.getYoutubeUrl())).toList();

        return new CourseDTO(course.getId(), course.getName(),
                course.getCategory().getValue(), lessons);
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) {
            return null;
        }
        Course course = new Course();

        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }

        course.setName(courseDTO.name());
        course.setCategory( convertCategoryValue(courseDTO.category()) );
        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }

}
