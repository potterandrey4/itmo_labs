package itmo.soa.labworkservice.controller;

import itmo.soa.labworkservice.model.Discipline;
import itmo.soa.labworkservice.service.LabworkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
@CrossOrigin(origins = "*")
@Validated
public class DisciplineController {
    private final LabworkService labworkService;

    public DisciplineController(LabworkService labworkService) {
        this.labworkService = labworkService;
    }

    @GetMapping
    public List<Discipline> getAllDisciplines() {
        return labworkService.getAllDisciplines();
    }

    @GetMapping("/{id}")
    public Discipline getDiscipline(@PathVariable("id") @Min(1) long id) {
        return labworkService.getDiscipline(id);
    }

    @PostMapping
    public ResponseEntity<Discipline> createDiscipline(@Valid @RequestBody DisciplineInput input) {
        Discipline created = labworkService.createDiscipline(input.getName(), input.getPracticeHours());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Discipline updateDiscipline(@PathVariable("id") @Min(1) long id, @Valid @RequestBody DisciplineInput input) {
        return labworkService.updateDiscipline(id, input.getName(), input.getPracticeHours());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiscipline(@PathVariable("id") @Min(1) long id) {
        labworkService.deleteDiscipline(id);
    }

    public static class DisciplineInput {
        @NotBlank(message = "name must not be blank")
        @Size(max = 255, message = "name must not exceed 255 characters")
        private String name;

        @PositiveOrZero(message = "practiceHours must be positive or zero")
        private int practiceHours;

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
}