package lowsw.service;

import lowsw.domain.Hero;
import lowsw.domain.Party;

// With the use of AI
public class BattleEngine implements IBattleService {

    @Override
    public BattleState initBattle(Party a, Party b) {
        if (a.getHeroes().isEmpty() || b.getHeroes().isEmpty()) {
            throw new IllegalArgumentException("Both parties must have at least 1 hero");
        }
        return new BattleState(a, b);
    }

    @Override
    public BattleState applyAction(BattleState state, Action action) {
        if (state.isFinished()) return state;

        Party attackerParty = state.getA();
        Party targetParty = state.getB();

        // For Deliverable 1: attackerIndex refers to party A, targetIndex refers to party B.
        // You will generalize this in Deliverables 2–3.
        Hero attacker = attackerParty.getHeroes().get(action.attackerIndex());
        Hero target = targetParty.getHeroes().get(action.targetIndex());

        switch (action.type()) {
            case ATTACK -> {
                int dmg = Math.max(0, attacker.getAtk() - target.getDef());
                target.setHp(target.getHp() - dmg);
            }
            case DEFEND -> {
                attacker.setHp(attacker.getHp() + 10);
                attacker.setMana(attacker.getMana() + 5);
            }
            case WAIT -> {
                // In full version: unit moves to end of queue FIFO. Here we keep it as a no-op.
            }
        }

        boolean allDeadB = targetParty.getHeroes().stream().allMatch(h -> h.getHp() <= 0);
        boolean allDeadA = attackerParty.getHeroes().stream().allMatch(h -> h.getHp() <= 0);

        if (allDeadB) state.finish("A_WINS");
        else if (allDeadA) state.finish("B_WINS");

        return state;
    }
}
