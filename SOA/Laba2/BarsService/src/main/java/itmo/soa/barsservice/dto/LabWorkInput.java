package itmo.soa.barsservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import itmo.soa.barsservice.model.Coordinates;
import itmo.soa.barsservice.model.Difficulty;
import itmo.soa.barsservice.model.LabWork;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabWorkInput {
    private String name;
    private Coordinates coordinates;
    private Double minimalPoint;
    private Integer personalQualitiesMaximum;
    private Difficulty difficulty;
    private long disciplineId;

    public static LabWorkInput fromLabWork(LabWork labWork) {
        LabWorkInput input = new LabWorkInput();
        input.setName(labWork.getName());
        input.setCoordinates(Coordinates.copyOf(labWork.getCoordinates()));
        input.setMinimalPoint(labWork.getMinimalPoint());
        input.setPersonalQualitiesMaximum(labWork.getPersonalQualitiesMaximum());
        input.setDifficulty(labWork.getDifficulty());
        input.setDisciplineId(labWork.getDisciplineId());
        return input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Double getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(Double minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public Integer getPersonalQualitiesMaximum() {
        return personalQualitiesMaximum;
    }

    public void setPersonalQualitiesMaximum(Integer personalQualitiesMaximum) {
        this.personalQualitiesMaximum = personalQualitiesMaximum;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(long disciplineId) {
        this.disciplineId = disciplineId;
    }
}
