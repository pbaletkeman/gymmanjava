package ca.letkeman.gymmanjava.model;

import jakarta.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "step")
public class Step {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "stepNum")
  private Long stepNum;

  @Column(name = "description")
  private String description;

  public Step() {
  }

  public Step(Long id, String name, Long stepNum, String description) {
    this.id = id;
    this.name = name;
    this.stepNum = stepNum;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getStepNum() {
    return stepNum;
  }

  public void setStepNum(Long stepNum) {
    this.stepNum = stepNum;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Step.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("name='" + name + "'")
        .add("stepNum=" + stepNum)
        .add("description='" + description + "'")
        .toString();
  }
}
