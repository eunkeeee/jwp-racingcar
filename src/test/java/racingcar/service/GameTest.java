package racingcar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static racingcar.exception.ExceptionMessage.EMPTY_CARS;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import racingcar.domain.CustomMoveStrategy;
import racingcar.domain.Game;
import racingcar.dto.NamesDto;
import racingcar.dto.ResultDto;
import racingcar.dto.WinnerDto;

class GameTest {
    private static final CustomMoveStrategy MOVE_STRATEGY = new CustomMoveStrategy(5);

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @ParameterizedTest
    @CsvSource(value = {"0:0", "1:0", "2:0", "3:0", "4:1", "5:1", "6:1", "7:1", "8:1", "9:1"}, delimiter = ':')
    @DisplayName("입력 값이 3 이하이면 자동차가 움직이면 안 된다.")
    void move_shouldNotMoveWhenNumberIsUnderThree(int input, int expected) {
        // given
        CustomMoveStrategy moveStrategy = new CustomMoveStrategy(input);
        game.createCars(NamesDto.of("car1"));
        game.moveCars(moveStrategy);

        // when
        List<ResultDto> carStatuses = game.getCarStatuses();

        // then
        assertThat(carStatuses.get(0).getPosition()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"car:1", "car1,car2:2", "car1,car2,car3:3"}, delimiter = ':')
    @DisplayName("자동차가 생성되어야 한다.")
    void car_create(String input, int expected) {
        // given
        game.createCars(NamesDto.of(input));

        // when
        List<ResultDto> carStatuses = game.getCarStatuses();

        // then
        assertThat(carStatuses.size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("자동차의 정보가 정상적으로 반환되어야 한다.")
    void car_getStatues() {
        // given
        game.createCars(NamesDto.of("car1"));

        // when
        List<ResultDto> carStatuses = game.getCarStatuses();

        // then
        assertThat(carStatuses.get(0).getPosition()).isEqualTo(0);
        assertThat(carStatuses.get(0).getName()).isEqualTo("car1");
    }

    @Test
    @DisplayName("우승자의 이름이 정상적으로 반환되어야 한다.")
    void findWinners() {
        // given
        game.createCars(NamesDto.of("car1,car2"));
        game.moveCars(MOVE_STRATEGY);

        // then
        WinnerDto winners = game.findWinners();
        assertThat(winners.getWinners().size())
                .isEqualTo(2);
        assertThat(winners.getWinners().get(0))
                .isEqualTo("car1");
        assertThat(winners.getWinners().get(1))
                .isEqualTo("car2");
    }

    @Test
    @DisplayName("자동차를 생성하지 않고 우승자를 찾으면 예외가 발생해야 한다.")
    void findWinners_emptyCars() {
        // expect
        assertThatThrownBy(() -> game.findWinners())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("자동차를 생성하지 않고 자동차의 상태를 가져오면 비어있어야 한다.")
    void getCarStatuses_empty() {
        // expect
        assertThatThrownBy(() -> game.getCarStatuses())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(EMPTY_CARS.getMessage());
    }

    @Test
    @DisplayName("자동차를 생성하지 않고 자동차를 움직이면 예외가 발생해야 한다.")
    void moveCars_empty() {
        // expect
        assertThatThrownBy(() -> game.moveCars(MOVE_STRATEGY))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(EMPTY_CARS.getMessage());
    }
}
