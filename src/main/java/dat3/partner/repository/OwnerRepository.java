package dat3.partner.repository;

import dat3.partner.entity.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    //Page<Owner> findByFirstNameContainingOrLastNameContainingIgnoreCase(String name, Pageable pageable);

    Owner findByMobile(String mobile);

    boolean existsOwnerByMobile(String mobile);
}
