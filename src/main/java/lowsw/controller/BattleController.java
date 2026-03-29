package lowsw.controller;

import lowsw.domain.Action;
import lowsw.domain.Party;
import lowsw.service.BattleState;
import lowsw.service.IBattleService;

// With the use of AI
public class BattleController {
    private final IBattleService battle;

    public BattleController(IBattleService battle) {
        this.battle = battle;
    }

    public BattleState start(Party a, Party b) {
        return battle.initBattle(a, b);
    }

    public BattleState act(BattleState state, Action action) {
        return battle.applyAction(state, action);
    }
}
