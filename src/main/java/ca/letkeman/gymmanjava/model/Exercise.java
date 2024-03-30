package ca.letkeman.gymmanjava.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "exercise")
public class Exercise {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;


  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "stepId")
  private Set<Step> steps = new HashSet<>();

  public Exercise(){}

  public Exercise (String name, String description){
    this.name = name;
    this.description = description;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Exercise.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("name='" + name + "'")
        .add("description='" + description + "'")
        .toString();
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Step> getSteps() {
    return steps;
  }

  public void setSteps(Set<Step> steps) {
    this.steps = steps;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Exercise exercise = (Exercise) o;
    return Objects.equals(getId(), exercise.getId()) && Objects.equals(getName(),
        exercise.getName()) && Objects.equals(getDescription(), exercise.getDescription())
        && Objects.equals(getSteps(), exercise.getSteps());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getDescription(), getSteps());
  }
}
