package itmo.soa.barsservice.service;

import itmo.soa.barsservice.client.LabworkApiClient;
import itmo.soa.barsservice.dto.LabWorkInput;
import itmo.soa.barsservice.model.Discipline;
import itmo.soa.barsservice.model.Difficulty;
import itmo.soa.barsservice.model.LabWork;
import org.springframework.stereotype.Service;

@Service
public class BarsOperationsService {
    private final LabworkApiClient labworkApiClient;

    public BarsOperationsService(LabworkApiClient labworkApiClient) {
        this.labworkApiClient = labworkApiClient;
    }

    public LabWork decreaseDifficulty(long labworkId, int steps) {
        if (steps < 1) {
            throw new IllegalArgumentException("stepsCount must be greater than 0");
        }
        LabWork labWork = labworkApiClient.getLabWork(labworkId);
        Difficulty current = labWork.getDifficulty() == null ? Difficulty.VERY_EASY : labWork.getDifficulty();
        Difficulty updatedDifficulty = current.decreasedBy(steps);
        labWork.setDifficulty(updatedDifficulty);
        LabWorkInput payload = LabWorkInput.fromLabWork(labWork);
        payload.setDifficulty(updatedDifficulty);
        return labworkApiClient.updateLabWork(labworkId, payload);
    }

    public LabWork assignDiscipline(long labworkId, Discipline discipline) {
        LabWork labWork = labworkApiClient.getLabWork(labworkId);
        labWork.setDisciplineId(discipline.getId());
        LabWorkInput payload = LabWorkInput.fromLabWork(labWork);
        payload.setDisciplineId(discipline.getId());
        return labworkApiClient.updateLabWork(labworkId, payload);
    }
}
