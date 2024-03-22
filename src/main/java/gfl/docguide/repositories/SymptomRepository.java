package gfl.docguide.repositories;

import gfl.docguide.data.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    Optional<Symptom> getByNameIgnoreCase(String name);


    boolean existsByNameIgnoreCase(String name);
}
