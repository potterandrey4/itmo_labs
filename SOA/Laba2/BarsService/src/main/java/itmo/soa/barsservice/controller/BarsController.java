package itmo.soa.barsservice.controller;

import itmo.soa.barsservice.model.Discipline;
import itmo.soa.barsservice.model.LabWork;
import itmo.soa.barsservice.service.BarsOperationsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bars")
@CrossOrigin(origins = "*")
public class BarsController {
    private final BarsOperationsService barsOperationsService;

    public BarsController(BarsOperationsService barsOperationsService) {
        this.barsOperationsService = barsOperationsService;
    }

    @PostMapping("/decrease-difficulty/{id}")
    public LabWork decreaseDifficulty(@PathVariable("id") @Min(1) long id) {
        return barsOperationsService.decreaseDifficulty(id, 1);
    }

    @PostMapping("/assign-discipline/{id}")
    public LabWork assignDiscipline(@PathVariable("id") @Min(1) long id, @Valid @RequestBody Discipline discipline) {
        return barsOperationsService.assignDiscipline(id, discipline);
    }
}
