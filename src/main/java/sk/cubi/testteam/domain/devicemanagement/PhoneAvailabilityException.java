package sk.cubi.testteam.domain.devicemanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception marking an inconsistency regarding phones availability, such as not being booked when attempting to return
 * or vice versa.
 */
public class PhoneAvailabilityException extends ResponseStatusException {
    public PhoneAvailabilityException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
