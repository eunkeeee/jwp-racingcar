package racingcar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("mycar");
    }

    @Test
    @DisplayName("차를 움직였을 때 위치가 변해야 한다.")
    void car_move() {
        // given
        int prevValue = car.getMovedLength().getValue();
        car.move();

        // expect
        assertThat(car.getMovedLength().getValue()).isGreaterThan(prevValue);
    }

    @Test
    @DisplayName("차의 정보가 정확하게 반환되어야 한다.")
    void car_status() {
        // given
        car.move();
        car.move();

        // expect
        assertThat(car.getMovedLength().getValue()).isEqualTo(2);
        assertThat(car.getName().getName()).isEqualTo("mycar");
    }
}
