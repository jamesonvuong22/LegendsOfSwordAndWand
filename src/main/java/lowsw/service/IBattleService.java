package lowsw.service;

import lowsw.domain.Party;

// With the use of AI
public interface IBattleService {
    BattleState initBattle(Party a, Party b);
    BattleState applyAction(BattleState state, Action action);
}
