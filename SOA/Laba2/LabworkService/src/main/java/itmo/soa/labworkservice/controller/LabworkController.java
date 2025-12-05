package itmo.soa.labworkservice.controller;

import itmo.soa.labworkservice.dto.LabWorkInput;
import itmo.soa.labworkservice.dto.PageLabWork;
import itmo.soa.labworkservice.dto.SearchDTO;
import itmo.soa.labworkservice.model.Difficulty;
import itmo.soa.labworkservice.model.LabWork;
import itmo.soa.labworkservice.service.LabworkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/labworks")
@CrossOrigin(origins = "*")
@Validated
public class LabworkController {
	private final LabworkService labworkService;

	public LabworkController(LabworkService labworkService) {
		this.labworkService = labworkService;
	}

	@PostMapping("/search")
	public PageLabWork searchLabworks(@Valid @ModelAttribute SearchDTO request) {
		return labworkService.searchLabWorks(request);
	}

	@PostMapping
	public ResponseEntity<LabWork> createLabwork(@Valid @RequestBody LabWorkInput labInput) {
		LabWork created = labworkService.createLabwork(labInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{id}")
	public LabWork getLabwork(@PathVariable("id") @Min(1) long id) {
		return labworkService.getLabwork(id);
	}

	@PutMapping("/{id}")
	public LabWork updateLabwork(@PathVariable("id") @Min(1) long id,
			@Valid @RequestBody LabWorkInput labInput) {
		return labworkService.updateLabwork(id, labInput);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLabwork(@PathVariable("id") @Min(1) long id) {
		labworkService.deleteLabwork(id);
	}

	@GetMapping("/by/discipline-groups")
	public Map<String, Long> byDiscipline() {
		return labworkService.groupByDiscipline();
	}

	@GetMapping("/by/prefixed-names")
	public List<LabWork> byPrefixedName(@RequestParam("prefix") @NotBlank String prefix) {
		return labworkService.findByNamePrefix(prefix);
	}

	@GetMapping("/by/difficulty-greater-than")
	public List<LabWork> byDifficulty(@RequestParam("difficulty") Difficulty difficulty) {
		return labworkService.findByDifficultyGreaterThan(difficulty);
	}
}
