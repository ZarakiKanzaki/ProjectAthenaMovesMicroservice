package com.projectathena.movesmicroservice.adapter.out.persistence;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;

@Repository
public class CoreMoveRepository  extends FaunaRepository<Move>implements CoreMovePort, GetCoreMoveQuery {

    private static final String MOVES = "moves";

    public CoreMoveRepository() {
        super(Move.class, MOVES);
    }

    @Override
    public Move getCoreMove(long moveId) throws ApplicationException {
        try {
            return client.query(
                            Select(
                                    Value("data"),
                                    Get(Ref(Value(moveId), className))
                            )
                    )
                    .thenApply(this::toEntity)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException();
        }
    }

    @Override
    public MoveRollResult getCoreMoveResult(short rollForMove, Move coreMove, CharacterPowerBeforeRoll rollPower) {
  /*      var rollResult = MoveRollResult.builder()
                .characterPowerBeforeRoll()*/

        return null;
    }
}
