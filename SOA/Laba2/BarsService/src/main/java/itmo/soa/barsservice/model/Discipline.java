package itmo.soa.barsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Discipline {
    @Min(1)
    private long id;
    @NotBlank
    private String name;
    @Min(0)
    private int practiceHours;

    public Discipline() {
    }

    public Discipline(String name, int practiceHours) {
        this.name = name;
        this.practiceHours = practiceHours;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static Discipline copyOf(Discipline source) {
        if (source == null) {
            return null;
        }
        return new Discipline(source.getName(), source.getPracticeHours());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPracticeHours() {
        return practiceHours;
    }

    public void setPracticeHours(int practiceHours) {
        this.practiceHours = practiceHours;
    }
}
