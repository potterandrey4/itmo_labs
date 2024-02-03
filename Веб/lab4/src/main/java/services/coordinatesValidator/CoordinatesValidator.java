package services.coordinatesValidator;

import entitiesDTO.CoordinatesDTO;
import exceptions.CoordinatesOutOfBoundsException;
import exceptions.EmptyCoordinateException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoordinatesValidator {
    public void validate(CoordinatesDTO coordinates){
        double x = Optional.of(coordinates.getX()).orElseThrow(() -> new EmptyCoordinateException("x"));
        double y = Optional.of(coordinates.getY()).orElseThrow(() -> new EmptyCoordinateException("y"));
        double r = Optional.of(coordinates.getR()).orElseThrow(() -> new EmptyCoordinateException("r"));

        /* if value was integer but stored like double rounding and casting
        it do long wouldn't change it but we would be able to check if it was integer */
        if (!(x >= -4 && x <= 4)) {
            throw new CoordinatesOutOfBoundsException(x, "x");
        }
        if (!(y >= -5 && y <= 5)) {
            throw new CoordinatesOutOfBoundsException(y, "y");
        }
        if (!(r >= 1 && r <= 5)) {
            throw new CoordinatesOutOfBoundsException(r, "r");
        }
    }
}
