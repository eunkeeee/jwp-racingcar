package racingcar.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static racingcar.exception.ExceptionMessage.EMPTY_CARS;

import java.util.List;
import racingcar.domain.Car;
import racingcar.domain.Cars;
import racingcar.domain.Position;
import racingcar.dto.NamesDto;
import racingcar.dto.ResultDto;
import racingcar.dto.WinnerDto;

public class RacingCarService {
    private Cars cars;

    public void createCars(NamesDto request) {
        this.cars = new Cars(request.getNames());
    }

    public void createCars(List<String> names) {
        this.cars = new Cars(names);
    }

    public void moveCars(MoveStrategy moveStrategy) {
        validateEmptyCars();
        for (Car car : cars.getCars()) {
            move(moveStrategy, car);
        }
    }

    private void validateEmptyCars() {
        if (cars == null) {
            throw new IllegalStateException(EMPTY_CARS.getMessage());
        }
    }

    private void move(MoveStrategy moveStrategy, Car car) {
        if (moveStrategy.isMovable()) {
            car.move();
        }
    }

    public List<ResultDto> getCarStatuses() {
        validateEmptyCars();
        return cars.getCars()
                .stream()
                .map(ResultDto::of)
                .collect(toList());
    }

    public WinnerDto findWinners() {
        Position maxPosition = getMaxPosition();
        List<Car> winners = cars.getCars()
                .stream()
                .filter(car -> car.isSamePosition(maxPosition))
                .collect(toList());
        return WinnerDto.of(winners);
    }

    private Position getMaxPosition() {
        validateEmptyCars();
        return cars.getCars()
                .stream()
                .map(Car::getMovedLength)
                .max(comparing(Position::getValue))
                .orElseThrow(() -> new IllegalStateException(EMPTY_CARS.getMessage()));
    }
}
