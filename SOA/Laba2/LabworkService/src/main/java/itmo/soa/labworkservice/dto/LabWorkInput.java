package itmo.soa.labworkservice.dto;

import itmo.soa.labworkservice.model.Coordinates;
import itmo.soa.labworkservice.model.Difficulty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class LabWorkInput {
    @NotBlank(message = "name must not be blank")
    @Size(max = 255, message = "name must not exceed 255 characters")
    private String name;

    @Valid
    @NotNull(message = "coordinates are required")
    private Coordinates coordinates;

    @NotNull(message = "minimalPoint is required")
    @DecimalMin(value = "0", inclusive = false, message = "minimalPoint must be greater than 0")
    private String minimalPoint;

    @Positive(message = "personalQualitiesMaximum must be greater than 0")
    private String personalQualitiesMaximum;

    @NotNull(message = "difficulty is required")
    private Difficulty difficulty;

    @NotNull(message = "disciplineId is required")
    private Long disciplineId;

    public LabWorkInput() {
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

    public String getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(String minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public String getPersonalQualitiesMaximum() {
        return personalQualitiesMaximum;
    }

    public void setPersonalQualitiesMaximum(String personalQualitiesMaximum) {
        this.personalQualitiesMaximum = personalQualitiesMaximum;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }
}
