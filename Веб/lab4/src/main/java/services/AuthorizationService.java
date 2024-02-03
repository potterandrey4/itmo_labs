package services;

import entities.Owner;
import entitiesDTO.OwnerDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final OwnerService ownerService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthorizationService(OwnerService ownerService, BCryptPasswordEncoder passwordEncoder ) {
        this.ownerService = ownerService;
        this.passwordEncoder = passwordEncoder;
    }

    //    @Transactional(rollbackFor = Exception.class)
    public String register(OwnerDTO ownerDTO) {
        try {
            ownerDTO.setPassword(passwordEncoder.encode(ownerDTO.getPassword()));
            Owner newOwner = ownerService.getOwnerFromDTO(ownerDTO);

            ownerService.addOwner(newOwner);
            return "User added successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}