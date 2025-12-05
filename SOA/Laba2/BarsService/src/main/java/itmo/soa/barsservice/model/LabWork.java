package itmo.soa.barsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabWork {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private OffsetDateTime creationDate;
    private String minimalPoint;
    private Integer personalQualitiesMaximum;
    private Difficulty difficulty;
    private long disciplineId;

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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(String minimalPoint) {
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
