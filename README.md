# jwp-racingcar

## 요구사항

### 웹 요청 / 응답 구현하기

- [x] POST로 `names` 와 `count`를 입력 받을 수 있다.
- [x] 우승자와 각 자동차들의 최종 위치를 JSON 형식으로 응답할 수 있다.

### DB 연동하기

- H2 DB를 연동할 수 있다.
    - [x] 플레이 횟수를 DB에 저장할 수 있다.
    - [x] 플레이어 이름, 최종 위치를 DB에 저장할 수 있다.
    - [x] 우승자를 DB에 저장할 수 있다.
    - [x] 플레이한 날짜/시간을 DB에 저장할 수 있다.

## DB 설계

### Game

- ID (id)
- 플레이 횟수 (trial_count)
- 플레이한 날짜/시간 (time)

### Player

- ID (id)
- 이름 (name)
- 최종 위치 (position)
- Game ID (game_id)
- 우승 여부 (is_winner)
