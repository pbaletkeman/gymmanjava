package ca.letkeman.gymmanjava.repository;

import ca.letkeman.gymmanjava.model.Exercise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

  List<Exercise> findByName(String name);

  List<Exercise> findByNameContaining(String name);

  Optional<Exercise> findById(long id);

}
