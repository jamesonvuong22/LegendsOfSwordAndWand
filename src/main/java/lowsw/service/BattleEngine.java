package lowsw.service;

import java.util.ArrayList;
import java.util.List;

import lowsw.domain.Action;
import lowsw.domain.Hero;
import lowsw.domain.IHero;
import lowsw.domain.Party;
import lowsw.domain.ShieldedHero;

public class BattleEngine implements IBattleService {
    private final DamageStrategy damageStrategy;
    private final List<BattleObserver> observers = new ArrayList<>();

    public BattleEngine(DamageStrategy damageStrategy) {
        this.damageStrategy = damageStrategy;
    }

    public void addObserver(BattleObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(BattleState state) {
        observers.forEach(o -> o.onBattleStateChanged(state));
    }

    @Override
    public BattleState initBattle(Party player, Party enemy) {
        if (player.getHeroes().isEmpty() || enemy.getHeroes().isEmpty()) {
            throw new IllegalArgumentException("Both parties need at least one hero");
        }
        BattleState state = new BattleState(player, enemy);
        notifyObservers(state);
        return state;
    }

    @Override
    public BattleState applyAction(BattleState state, Action action) {
        if (state.isFinished()) {
            return state;
        }

        Hero actor = state.getPlayerParty().getHero(action.actorIndex());
        Hero target = state.getEnemyParty().getHero(action.targetIndex());

        switch (action.type()) {
            case ATTACK -> {
                int damage = damageStrategy.computeDamage(actor, target);
                target.setHp(target.getHp() - damage);
            }
            case DEFEND -> {
                IHero shielded = new ShieldedHero(actor);
                actor.setHp(actor.getHp() + 10);
                actor.setMana(actor.getMana() + 5);

                int shieldValue = shielded.getDefensePower();
                if (shieldValue < 0) {
                    throw new IllegalStateException("Invalid shield");
                }
            }
            case WAIT -> actor.setMana(actor.getMana() + 2);
            case CAST -> {
                if (actor.getMana() < actor.spellCost()) {
                    throw new IllegalStateException("Not enough mana");
                }
                actor.setMana(actor.getMana() - actor.spellCost());
                target.setHp(target.getHp() - actor.spellDamage());
            }
        }

        if (state.getEnemyParty().isDefeated()) {
            state.finish("PLAYER_WINS");
        }
        if (state.getPlayerParty().isDefeated()) {
            state.finish("ENEMY_WINS");
        }

        notifyObservers(state);
        return state;
    }
}
