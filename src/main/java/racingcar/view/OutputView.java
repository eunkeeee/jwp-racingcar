package racingcar.view;

import racingcar.dto.RacingCarResultDto;
import racingcar.dto.WinnerDto;

public class OutputView {

    public static void printStartMessage() {
        System.out.println("실행 결과");
    }

    public static void printWinners(WinnerDto response) {
        System.out.println(response + "가 최종 우승했습니다.");
    }

    public static void printRacingProgress(RacingCarResultDto response) {
        System.out.println(response);
    }

    public static void printExceptionMessage(RuntimeException e) {
        System.out.println(e.getMessage());
    }
}
