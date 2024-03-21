package gfl.docguide.repositories;

import gfl.docguide.data.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    @Query("SELECT DISTINCT d FROM Disease d JOIN d.symptoms s WHERE s.id IN :symptomIds")
    List<Disease> findBySymptomIds(@Param("symptomIds") List<Long> symptomIds);
}
