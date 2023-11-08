package com.hailton.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.hailton.crudspring.enums.Category;
import com.hailton.crudspring.model.Course;
import com.hailton.crudspring.model.Lesson;
import com.hailton.crudspring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDataBase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();

            for (int i = 0; i < 20; i++) {
                Course c = new Course();
                c.setName("Angular com Spring " + i);
                c.setCategory(Category.BACK_END);

                Lesson l = new Lesson();
                l.setName("Introdução " + i);
                l.setYoutubeUrl("watch?v123");
                l.setCourse(c);
                c.getLessons().add(l);

                Lesson l1 = new Lesson();
                l1.setName("Angular " + i);
                l1.setYoutubeUrl("watch?v234");
                l1.setCourse(c);
                c.getLessons().add(l1);

                courseRepository.save(c);
            }
        };
    }

}
