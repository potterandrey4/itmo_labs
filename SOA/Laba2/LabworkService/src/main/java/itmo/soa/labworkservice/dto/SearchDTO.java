package itmo.soa.labworkservice.dto;

import itmo.soa.labworkservice.model.Difficulty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class SearchDTO {
    @Min(0)
    private Integer page = 0;

    @Min(1)
    @Max(100)
    private Integer size = 10;

    private String sort;
    private String name;
    private Difficulty minDifficulty;

    @DecimalMin(value = "0", inclusive = false)
    private Double minimalPointGreaterThan;

    @Positive
    private Integer personalQualitiesMaximumGreaterThan;

    private Double xGreaterThan;
    private Double xLessThan;
    private Double yGreaterThan;
    private Double yLessThan;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime creationDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime creationDateTo;

    private String disciplineName;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getMinDifficulty() {
        return minDifficulty;
    }

    public void setMinDifficulty(Difficulty minDifficulty) {
        this.minDifficulty = minDifficulty;
    }

    public Double getMinimalPointGreaterThan() {
        return minimalPointGreaterThan;
    }

    public void setMinimalPointGreaterThan(Double minimalPointGreaterThan) {
        this.minimalPointGreaterThan = minimalPointGreaterThan;
    }

    public Integer getPersonalQualitiesMaximumGreaterThan() {
        return personalQualitiesMaximumGreaterThan;
    }

    public void setPersonalQualitiesMaximumGreaterThan(Integer personalQualitiesMaximumGreaterThan) {
        this.personalQualitiesMaximumGreaterThan = personalQualitiesMaximumGreaterThan;
    }

    public Double getXGreaterThan() {
        return xGreaterThan;
    }

    public void setXGreaterThan(Double xGreaterThan) {
        this.xGreaterThan = xGreaterThan;
    }

    public Double getXLessThan() {
        return xLessThan;
    }

    public void setXLessThan(Double xLessThan) {
        this.xLessThan = xLessThan;
    }

    public Double getYGreaterThan() {
        return yGreaterThan;
    }

    public void setYGreaterThan(Double yGreaterThan) {
        this.yGreaterThan = yGreaterThan;
    }

    public Double getYLessThan() {
        return yLessThan;
    }

    public void setYLessThan(Double yLessThan) {
        this.yLessThan = yLessThan;
    }

    public OffsetDateTime getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(OffsetDateTime creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public OffsetDateTime getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(OffsetDateTime creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }
}
