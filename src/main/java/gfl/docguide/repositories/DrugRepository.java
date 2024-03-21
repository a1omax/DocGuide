package gfl.docguide.repositories;

import gfl.docguide.data.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    @Transactional
    @Modifying
    @Query("update Drug d set d.amount = :amount where d.id = :id")
    int updateAmountById(@NonNull @Param("amount") Integer amount, @Param("id") Long id);

    List<Drug> findByActiveSubstanceId(long id);

    //List<Drug> findAllByActiveSubstance_Id(Long activeSubstanceId);
}
