package be.odisee.jzzz.dao;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import be.odisee.jzzz.domain.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, Integer> {
	
	Optional<Evenement> findByDatumBetween(@Param("datumvan") @DateTimeFormat(iso = ISO.DATE)  Date datumvan, 
												@Param("datumtot") @DateTimeFormat(iso = ISO.DATE)  Date datumtot);

}
