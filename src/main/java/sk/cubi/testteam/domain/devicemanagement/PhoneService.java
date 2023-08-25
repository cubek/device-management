package sk.cubi.testteam.domain.devicemanagement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * A service layer for business operations regarding phones - mostly booking and returning, plus querying.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PhoneService {

    private final PhoneRepository repository;

    /**
     * Get all phones.
     *
     * @return list of maintained phones
     */
    public List<Phone> getPhones() {
        return repository.findAll();
    }

    /**
     * Get a particular phone.
     *
     * @param id system identifier of the phone
     * @return phone
     * @throws PhoneNotFoundException in case the phone is not found
     */
    public Phone getPhone(Long id) {
        return repository.findById(id).orElseThrow(PhoneNotFoundException::new);
    }

    /**
     * Book a phone.
     *
     * @param id   system identifier of the phone
     * @param user name of the user who is booking the phone
     * @return updated data of the phone
     * @throws PhoneNotFoundException     if the phone is not found
     * @throws PhoneAvailabilityException if the phone is not available (already booked)
     */
    public Phone bookPhone(Long id, String user) {
        return repository.findById(id)
                .map(p -> {
                    if (p.isAvailable()) {
                        p.setAvailable(false).setBookedBy(user).setBookedOn(LocalDate.now());
                    } else {
                        throw new PhoneAvailabilityException("Phone is already booked by another user");
                    }
                    return p;
                })
                .orElseThrow(PhoneNotFoundException::new);
    }

    /**
     * Return a booked phone
     *
     * @param id system identifier of the phone
     * @return updated data of the phone
     * @throws PhoneNotFoundException     if the phone is not found
     * @throws PhoneAvailabilityException if the phone was not booked previously (is still available)
     */
    public Phone returnPhone(Long id) {
        return repository.findById(id)
                .map(p -> {
                    if (p.isAvailable()) {
                        throw new PhoneAvailabilityException("Phone is not booked");
                    } else {
                        p.setAvailable(true).setBookedBy(null).setBookedOn(null);
                    }
                    return p;
                })
                .orElseThrow(PhoneNotFoundException::new);
    }
}
