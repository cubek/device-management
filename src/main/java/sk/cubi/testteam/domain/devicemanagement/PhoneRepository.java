package sk.cubi.testteam.domain.devicemanagement;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Phone repository, standard JPA implementation for querying DB layer.
 */
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
