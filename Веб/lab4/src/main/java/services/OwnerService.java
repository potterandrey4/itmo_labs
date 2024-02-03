package services;

import entities.Owner;
import entitiesDTO.OwnerDTO;
import exceptions.OwnerAlreadyExistsException;
import exceptions.OwnerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.OwnersRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Log
@Service
public class OwnerService implements UserDetailsService {
    private final OwnersRepository repository;
    private final BCryptPasswordEncoder passwordEncoder; //FIXME this field needed only for tests

    @Autowired
    public OwnerService(OwnersRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        //addUserTest(); //todo: remove test
    }

    public List<Owner> allOwners() {
        return repository.findAll();
    }

    public Owner addOwner(Owner newOwner) {
        if(checkOwner(newOwner.getLogin())) throw  new OwnerAlreadyExistsException(newOwner.getLogin());
        return repository.save(newOwner);
    }

    public void updateOwner(Owner newOwner) {
        repository.save(newOwner);
    }

    public Owner getOwner(String ownerLogin) {
        try {
            return repository.findById(ownerLogin).orElseThrow(() -> new OwnerNotFoundException(ownerLogin));
        }catch (EntityNotFoundException e){
            throw new OwnerNotFoundException(ownerLogin);
        }
    }

    public Owner replaceOwner(Owner newOwner, String ownerLogin) {

        return repository.findById(ownerLogin)
                .map(owner -> {
                    owner.setPassword(newOwner.getPassword());
                    owner.setAttemptList(newOwner.getAttemptList()); //todo:check that here we put the whole object and check if it is authorized
                    return repository.save(owner);
                })
                .orElseGet(() -> {
                    newOwner.setLogin(ownerLogin);
                    return repository.save(newOwner);
                });
    }

    public void deleteOwner(String login) {
        repository.deleteById(login);
    }

    @Override
    public User loadUserByUsername(String login) throws UsernameNotFoundException {
        Owner owner = getOwner(login);
        if (Objects.isNull(owner)) {
            throw new OwnerNotFoundException(login);
        }
        return new User(owner.getLogin(), owner.getPassword(), true, true, true, true, new HashSet<>());
    }

    public Owner getOwnerFromDTO(OwnerDTO ownerDTO){
        return new Owner(ownerDTO.getLogin(), ownerDTO.getPassword());
    }

    public  boolean checkOwner(String login){
        return repository.findById(login).isPresent();
    }

}
