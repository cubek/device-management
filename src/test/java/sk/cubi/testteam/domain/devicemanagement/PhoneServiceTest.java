package sk.cubi.testteam.domain.devicemanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

    @Test
    @DisplayName("given repository returns list of phones on findAll when getPhones() is called then such list is returned")
    void getPhonesReturnsList(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(List.of(new Phone().setName("iphone"))).when(repository).findAll();

        var result = cut.getPhones();

        assertThat(result).isEqualTo(List.of(new Phone().setName("iphone")));
    }

    @Test
    @DisplayName("given repository returns iphone on findById(4L) when getPhone(4L) is called then iphone is returned")
    void getPhoneWhenExists(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.of(new Phone().setName("iphone"))).when(repository).findById(4L);

        var result = cut.getPhone(4L);

        assertThat(result).isEqualTo(new Phone().setName("iphone"));
    }

    @Test
    @DisplayName("given repository returns empty optional on findById(4L) when getPhone(4L) is called then PhoneNotFoundException is thrown")
    void getPhoneWhenDoesntExist(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.empty()).when(repository).findById(4L);

        assertThatExceptionOfType(PhoneNotFoundException.class).isThrownBy(() -> cut.getPhone(4L));
    }

    @Test
    @DisplayName("given repository returns available iphone on findById(4L) when bookPhone(4L, johnny) is called then iphone is returned")
    void bookPhoneWhenAvailableExists(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.of(new Phone().setName("iphone").setAvailable(true))).when(repository).findById(4L);

        var result = cut.bookPhone(4L, "johnny");

        assertThat(result).isEqualTo(new Phone().setName("iphone").setAvailable(false).setBookedBy("johnny").setBookedOn(LocalDate.now()));
    }

    @Test
    @DisplayName("given repository returns unavailable iphone on findById(4L) when bookPhone(4L, johnny) is called then PhoneAvailabilityException is thrown")
    void bookPhoneWhenUnavailableExists(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.of(new Phone().setName("iphone").setAvailable(false))).when(repository).findById(4L);

        assertThatExceptionOfType(PhoneAvailabilityException.class).isThrownBy(() -> cut.bookPhone(4L, "johnny"));
    }

    @Test
    @DisplayName("given repository returns empty optional on findById(4L) when bookPhone(4L, johnny) is called then PhoneNotFoundException is thrown")
    void bookPhoneWhenDoesNotExist(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.empty()).when(repository).findById(4L);

        assertThatExceptionOfType(PhoneNotFoundException.class).isThrownBy(() -> cut.bookPhone(4L, "johnny"));
    }

    @Test
    @DisplayName("given repository returns unavailable iphone on findById(4L) when returnPhone(4L, johnny) is called then iphone is returned")
    void returnPhoneWhenAvailableExists(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.of(new Phone().setName("iphone").setAvailable(false))).when(repository).findById(4L);

        var result = cut.returnPhone(4L);

        assertThat(result).isEqualTo(new Phone().setName("iphone").setAvailable(true));
    }

    @Test
    @DisplayName("given repository returns available iphone on findById(4L) when returnPhone(4L, johnny) is called then PhoneAvailabilityException is thrown")
    void returnPhoneWhenUnavailableExists(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.of(new Phone().setName("iphone").setAvailable(true))).when(repository).findById(4L);

        assertThatExceptionOfType(PhoneAvailabilityException.class).isThrownBy(() -> cut.returnPhone(4L));
    }

    @Test
    @DisplayName("given repository returns empty optional on findById(4L) when returnPhone(4L) is called then PhoneNotFoundException is thrown")
    void returnPhoneWhenDoesNotExist(@Mock PhoneRepository repository) {
        var cut = new PhoneService(repository);
        doReturn(Optional.empty()).when(repository).findById(4L);

        assertThatExceptionOfType(PhoneNotFoundException.class).isThrownBy(() -> cut.bookPhone(4L, "johnny"));
    }
}