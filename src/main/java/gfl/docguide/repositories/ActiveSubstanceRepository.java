package gfl.docguide.repositories;

import gfl.docguide.data.ActiveSubstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveSubstanceRepository extends JpaRepository<ActiveSubstance, Long> {
    Optional<ActiveSubstance> findByName(String name);

    boolean existsByNameIgnoreCase(String name);

}
