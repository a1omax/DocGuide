package gfl.docguide.repositories;

import gfl.docguide.data.ActiveSubstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveSubstanceRepository extends JpaRepository<ActiveSubstance, Long> {
    Optional<ActiveSubstance> findByName(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("select (count(a) > 0) from ActiveSubstance a where upper(a.name) = upper(?1) and a.id <> ?2")
    boolean existsByNameIgnoreCaseAndIdIsNot(String name, Long Id);
}
