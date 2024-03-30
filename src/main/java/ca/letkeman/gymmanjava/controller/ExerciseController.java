package ca.letkeman.gymmanjava.controller;

import ca.letkeman.gymmanjava.model.Exercise;
import ca.letkeman.gymmanjava.repository.ExerciseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ExerciseController {

  @Autowired
  ExerciseRepository exerciseRepository;


  @GetMapping("/exercises")
  public ResponseEntity<List<Exercise>> getAllExercises(@RequestParam(required = false) String name){
    List<Exercise> exercises = new ArrayList<>();

    if (name == null){
      exercises.addAll(exerciseRepository.findAll());
    } else {
      exercises.addAll(exerciseRepository.findByNameContaining(name));
    }

    if (exercises.isEmpty()){
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(exercises, HttpStatus.OK);
  }

  @GetMapping("/exercise/{id}")
  public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") long id){
    Optional<Exercise> exercise = exerciseRepository.findById(id);
    return exercise.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(new Exercise(), HttpStatus.NOT_FOUND));
  }

  @PostMapping("/exercise")
  public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise){
    Exercise exercise1 = exerciseRepository.save(new Exercise(exercise.getName(), exercise.getDescription()));
    return new ResponseEntity<>(exercise1, HttpStatus.OK);
  }

  @PutMapping("/exercise/{id}")
  public ResponseEntity<Exercise> updateExercise(@PathVariable("id") long id, @RequestBody Exercise exercise){
    Optional<Exercise> exercise1 = exerciseRepository.findById(id);
    if (exercise1.isEmpty()){
      return new ResponseEntity<>(new Exercise(), HttpStatus.NOT_FOUND);
    }
    exercise1.get().setDescription(exercise.getDescription());
    exercise1.get().setName(exercise.getName());
    return new ResponseEntity<>(exerciseRepository.save(exercise1.get()), HttpStatus.OK);
  }

  @DeleteMapping("/exercise/{id}")
  public ResponseEntity<HttpStatus> deleteExercise(@PathVariable("id") long id) {
    exerciseRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/exercise")
  public ResponseEntity<HttpStatus> deleteAllExercises() {
    exerciseRepository.deleteAll();

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
