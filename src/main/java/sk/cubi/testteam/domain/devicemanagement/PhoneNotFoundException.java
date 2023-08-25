package sk.cubi.testteam.domain.devicemanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * An exception marking the phone as unavailable.
 */
public class PhoneNotFoundException extends ResponseStatusException {
    public PhoneNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Phone was not found");
    }
}
