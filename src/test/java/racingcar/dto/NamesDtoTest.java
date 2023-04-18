package racingcar.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static racingcar.exception.ExceptionMessage.OUT_OF_CAR_NAME_LENGTH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.domain.Car;

class NamesDtoTest {
    @ParameterizedTest
    @DisplayName("자동차의 이름이 6글자를 초과하면 예외가 발생해야 한다.")
    @ValueSource(strings = {"carcar", "car,carcar", "car,car2,carcar"})
    void car_nameLengthOverThanSix(String input) {
        // expect
        assertThatThrownBy(() -> new Car(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OUT_OF_CAR_NAME_LENGTH.getMessage());
    }
}
