package rest;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import security.SecurityConstants;
import services.AuthorizationService;
import entitiesDTO.OwnerDTO;

@Log
@RestController
public class AuthController {

    private final AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = SecurityConstants.SIGN_UP_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestBody OwnerDTO owner) {
        log.info("Starting registration!");
        return authorizationService.register(owner);
    }

}