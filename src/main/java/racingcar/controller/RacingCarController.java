package racingcar.controller;

import java.util.List;
import racingcar.dto.RacingCarNamesRequest;
import racingcar.dto.RacingCarResultDto;
import racingcar.dto.WinnerDto;
import racingcar.dto.TryCountRequest;
import racingcar.service.RacingCarService;
import racingcar.service.RandomMoveStrategy;
import racingcar.service.TryCount;
import racingcar.view.RacingCarView;

public class RacingCarController {
    private final RacingCarService racingCarService;
    private final RacingCarView racingCarView;

    public RacingCarController(RacingCarService racingCarService, RacingCarView racingCarView) {
        this.racingCarService = racingCarService;
        this.racingCarView = racingCarView;
    }

    public void start() {
        createCar();
        TryCount tryCount = new TryCount(getTryCount());
        playGame(tryCount);
    }

    private void createCar() {
        RacingCarNamesRequest racingCarNamesRequest = receiveCarNames();
        racingCarService.createCars(racingCarNamesRequest);
    }

    private RacingCarNamesRequest receiveCarNames() {
        try {
            return racingCarView.receiveCarNames();
        } catch (RuntimeException e) {
            racingCarView.printExceptionMessage(e);
            return receiveCarNames();
        }
    }

    private int getTryCount() {
        TryCountRequest tryCountRequest = receiveTryCount();
        return tryCountRequest.getTryCount();
    }

    private TryCountRequest receiveTryCount() {
        try {
            return racingCarView.receiveTryCount();
        } catch (RuntimeException e) {
            racingCarView.printExceptionMessage(e);
            return receiveTryCount();
        }
    }

    private void playGame(TryCount tryCount) {
        RandomMoveStrategy randomMoveStrategy = new RandomMoveStrategy();
        racingCarView.printStartMessage();
        while (tryCount.isAvailable()) {
            racingCarService.moveCars(randomMoveStrategy);
            printCarStatuses();
            tryCount.moveUntilZero();
        }
        printCarStatuses();
        findWinners();
    }

    private void findWinners() {
        try {
            WinnerDto winnerDto = racingCarService.findWinners();
            racingCarView.printWinners(winnerDto);
        } catch (RuntimeException e) {
            racingCarView.printExceptionMessage(e);
        }
    }

    private void printCarStatuses() {
        List<RacingCarResultDto> carStatuses = racingCarService.getCarStatuses();
        racingCarView.printRacingProgress(carStatuses);
    }
}
