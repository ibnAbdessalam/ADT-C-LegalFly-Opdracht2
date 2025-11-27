package be.odisee.jzzz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import be.odisee.jzzz.domain.Toegang;

public interface ToegangRepository extends JpaRepository<Toegang, Integer> {
	
	List<Toegang> findByEvenementidOrderByCreated(@Param("evenementid") int evenementid);
}
