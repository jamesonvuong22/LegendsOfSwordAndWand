package lowsw.service;

import lowsw.domain.Action;
import lowsw.domain.Party;

// With the use of AI
public interface IBattleService {
    BattleState initBattle(Party player, Party enemy);
    BattleState applyAction(BattleState state, Action action);
}
