package ca.letkeman.gymmanjava.controller;

import ca.letkeman.gymmanjava.model.Exercise;
import ca.letkeman.gymmanjava.model.Step;
import ca.letkeman.gymmanjava.repository.ExerciseRepository;
import ca.letkeman.gymmanjava.repository.StepRepository;
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
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StepController {

  @Autowired
  private StepRepository stepRepository;

  @Autowired
  private ExerciseRepository exerciseRepository;

  @GetMapping("/exercise/{exerciseId}/steps")
  public ResponseEntity<List<Step>> getAllStepsByExerciseId(@PathVariable (value = "exerciseId") Long exerciseId){
    Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
    if (exercise.isEmpty()){
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }
    List<Step> steps = new ArrayList<Step>(exercise.get().getSteps());
    return new ResponseEntity<>(steps, HttpStatus.OK);
  }

  @GetMapping("/steps/{id}")
  public ResponseEntity<Step> getStepById(@PathVariable(value = "id") Long id){
    Optional<Step> step = stepRepository.findById(id);
    return step.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(new Step(), HttpStatus.NOT_FOUND));
  }

  @PostMapping("/exercise/{exerciseId}/step")
  public ResponseEntity<Step> createStep(@PathVariable (value = "exerciseId") Long exerciseId, @RequestBody Step stepRequest){
    Optional<Step> step = exerciseRepository.findById(exerciseId).map(exercise -> {
      exercise.getSteps().add(stepRequest);
      return stepRepository.save(stepRequest);
    });
    return step.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
        .orElseGet(() -> new ResponseEntity<>(new Step(), HttpStatus.NOT_FOUND));
  }

  @PutMapping("/steps/{id}")
  public ResponseEntity<Step> updateStep(@PathVariable("id") long id, @RequestBody Step stepRequest){
    Step step = stepRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("record not found"));
    step.setDescription(stepRequest.getDescription());
    step.setStepNum(stepRequest.getStepNum());
    step.setName(stepRequest.getName());
    return new ResponseEntity<>(stepRepository.save(step), HttpStatus.OK);
  }

  @DeleteMapping("/steps/{id}")
  public ResponseEntity<HttpStatus> deleteStep(@PathVariable("id") long id) {
    stepRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
