package itmo.soa.labworkservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class Discipline {
    private long id;
    @NotBlank(message = "Discipline name must not be blank")
    private String name;

    @PositiveOrZero(message = "practiceHours must be positive or zero")
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
