package dat3.partner.repository;

import dat3.partner.entity.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    //Page<Owner> findByFirstNameContainingOrLastNameContainingIgnoreCase(String name, Pageable pageable);

    Owner findByMobile(String mobile);

    boolean existsOwnerByMobile(String mobile);

    //List<Owner> findAllByEmailContainsIgnoreCase(String searchFirst);

    //List<Owner> findAllByFirstNameOrLastNameOrEmailLikeIgnoreCase(String searchFirstName, String searchLastName, String searchEmail);

    @Query("SELECT o FROM Owner o " +
            "WHERE LOWER(o.firstName) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
            "   OR LOWER(o.lastName) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
            "   OR LOWER(o.email) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Owner> findAllBySearchValueIgnoreCase(@Param("searchValue") String searchValue);

    @Query("SELECT o FROM Owner o INNER JOIN o.unitList u WHERE u.unitNumber = :unitNumber")
    List<Owner> findOwnersByUnitNumber(@Param("unitNumber") String unitNumber);
}

