package com.hailton.crudspring.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.hailton.crudspring.dto.CourseDTO;
import com.hailton.crudspring.dto.CoursePageDTO;
import com.hailton.crudspring.exception.RecordNotFoundException;
import com.hailton.crudspring.model.Course;
import com.hailton.crudspring.model.mapper.CourseMapper;
import com.hailton.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO findAll(int page, int pageSize) {
        Page<Course> pageCourse = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).toList();
        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }

    /*
    public List<CourseDTO> findAll() {
        return courseRepository.findAll(PageRequest.of(0, 15))
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream().map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }
     */

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO( courseRepository.save(courseMapper.toEntity(course)) );
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id).map(recordFound -> {
            Course course = courseMapper.toEntity(courseDTO);
            recordFound.setName(courseDTO.name());
            recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));

            recordFound.getLessons().clear();
            course.getLessons().forEach(recordFound.getLessons()::add);

            return courseMapper.toDTO( courseRepository.save(recordFound) );
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
