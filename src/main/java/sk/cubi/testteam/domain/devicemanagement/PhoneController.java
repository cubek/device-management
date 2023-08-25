package sk.cubi.testteam.domain.devicemanagement;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Phones REST controller with API for interaction with the backend service.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/phones")
public class PhoneController {

    private final PhoneService phoneService;

    /**
     * Read all phones.
     *
     * @return list of maintained phones
     */
    @GetMapping
    public List<Phone> getPhones() {
        return phoneService.getPhones();
    }

    /**
     * Read a phone by id.
     *
     * @param id system identifier of the phone
     * @return data of the phone or 404 not found
     */
    @GetMapping("/{id}")
    public Phone getPhone(@PathVariable Long id) {
        return phoneService.getPhone(id);
    }

    /**
     * Operation to book a phone.
     *
     * @param id   system identifier of the phone
     * @param user user data supplied by spring
     * @return updated data of the booked phone, 404 in case the phone was not found, 400 in case it is unavailable
     */
    @PostMapping("/book/{id}")
    public Phone bookPhone(@PathVariable Long id, Principal user) {
        return phoneService.bookPhone(id, user.getName());
    }

    /**
     * Return a booked phone.
     *
     * @param id system identifier of the phone
     * @return updated data of the returned phone, 404 in case the phone was not found, 400 in case it is unavailable
     */
    @PostMapping("/return/{id}")
    public Phone returnPhone(@PathVariable Long id) {
        return phoneService.returnPhone(id);
    }
}
