package services;

import entities.Attempt;
import entities.Coordinates;
import entities.Owner;
import entitiesDTO.AttemptDTO;
import entitiesDTO.CoordinatesDTO;
import exceptions.CoordinatesOutOfBoundsException;
import exceptions.EmptyCoordinateException;
import lombok.extern.java.Log;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import services.areaChecker.AreaChecker;
import services.areaChecker.CheckerBuilder;
import services.coordinatesValidator.CoordinatesValidator;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class AttemptsService {
    private final OwnerService service;
    private final AreaChecker areaChecker;
    private final CoordinatesValidator coordinatesValidator;

    public AttemptsService(OwnerService service, CheckerBuilder checkerBuilder, CoordinatesValidator coordinatesValidator) {
        this.areaChecker = checkerBuilder
                .initAreaChecker()
                .addSquare1Quoter()
                .addCircleIn3Quoter()
                .addTriangleIn4Quoter()
                .getChecker();
        this.service = service;
        this.coordinatesValidator = coordinatesValidator;
    }

    public List<AttemptDTO> getAllAttempts() {
        List<AttemptDTO> resultList = new ArrayList<>();
        service.getOwner(getCurrentOwnerLogin()).getAttemptList().forEach(attempt -> {
                    Coordinates c = attempt.getCoordinates();
                    resultList.add(new AttemptDTO(c.getX(), c.getY(), c.getR(), attempt.getDoFitArea()));
                }
        );
        return resultList;
    }

    public AttemptDTO addAttempt(CoordinatesDTO coords) throws EmptyCoordinateException, CoordinatesOutOfBoundsException {
        try {
            coordinatesValidator.validate(coords); //if validation fails throws exceptions


            //update owner by extra attempt
            Attempt newAttempt = new Attempt(new Coordinates(coords.getX(), coords.getY(), coords.getR()), areaChecker.check(coords));
            Owner newAttemptOwner = service.getOwner(getCurrentOwnerLogin()); //un(log in) users can't addAttempts
            newAttempt.setOwner(newAttemptOwner);
            newAttempt.getCoordinates().setAttempt(newAttempt);
            newAttemptOwner.getAttemptList().add(newAttempt);
            service.updateOwner(newAttemptOwner);
            return new AttemptDTO(coords.getX(), coords.getY(), coords.getR(), newAttempt.getDoFitArea());
        }catch (CoordinatesOutOfBoundsException e){
            return null;
        }
    }

    //todo: do i need this methods:
    // Attempt getAttempt();
    // Attempt replaceAttempt(Attempt newAttempt, Long id);
    // void deleteAttempt(Long id);

    public void deleteAllAttempts(){
        service.getOwner(getCurrentOwnerLogin()).setAttemptList(new ArrayList<>());
    }


    private String getCurrentOwnerLogin() {
        return (SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toString(); //fixme: check that this cast is ok
    }
}