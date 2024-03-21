package gfl.docguide.repositories;

import gfl.docguide.data.TreatmentProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TreatmentProtocolRepository extends JpaRepository<TreatmentProtocol, Long> {
    List<TreatmentProtocol> findAllByDisease_Id(Long id);
}
