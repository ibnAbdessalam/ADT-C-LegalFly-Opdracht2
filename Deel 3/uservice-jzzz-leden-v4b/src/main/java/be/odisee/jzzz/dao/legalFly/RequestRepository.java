package be.odisee.jzzz.dao.legalFly;

import be.odisee.jzzz.domain.legalFly.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface RequestRepository  extends JpaRepository<Request, Long>  {
    List<Request> findByStatus(String status);
    List<Request> findByClientEmail(String clientEmail);
}
