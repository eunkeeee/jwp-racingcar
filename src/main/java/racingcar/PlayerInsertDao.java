package racingcar;

import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import racingcar.dto.RacingCarResultDto;

@Repository
public class PlayerInsertDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PlayerInsertDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPlayer(List<RacingCarResultDto> responses, List<String> winnerNames, int gameId) {
        String sql = "INSERT INTO player(name, position, game_id, is_winner) VALUES(:name, :position, :game_id, :is_winner)";

        System.out.println(gameId);
        for (RacingCarResultDto response : responses) {
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("name", response.getName())
                    .addValue("position", response.getPosition())
                    .addValue("game_id", gameId)
                    .addValue("is_winner", winnerNames.contains(response.getName()));

            jdbcTemplate.update(sql, namedParameters);
        }
    }
}
