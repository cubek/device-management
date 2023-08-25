package sk.cubi.testteam.domain.devicemanagement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * Phone representation.
 */
@Data
@Entity
@Accessors(chain = true)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean available = true;
    private String bookedBy;
    private LocalDate bookedOn;

}
