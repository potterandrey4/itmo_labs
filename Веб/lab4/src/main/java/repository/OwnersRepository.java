package repository;

import entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnersRepository extends JpaRepository<Owner, String> {}
