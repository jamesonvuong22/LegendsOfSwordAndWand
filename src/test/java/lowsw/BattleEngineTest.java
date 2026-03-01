package lowsw;

import lowsw.domain.*;
import lowsw.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// With the use of AI
public class BattleEngineTest {

    @Test
    void attack_decreases_hp_by_max0_atk_minus_def() {
        Party a = new Party(0);
        a.addHero(new Hero("A", ClassType.ORDER, 1, 50, 0, 10, 0));
        Party b = new Party(0);
        b.addHero(new Hero("B", ClassType.CHAOS, 1, 40, 0, 0, 3));

        IBattleService engine = new BattleEngine();
        BattleState st = engine.initBattle(a, b);
        engine.applyAction(st, new Action(0,0, ActionType.ATTACK));

        assertEquals(40 - (10-3), b.getHeroes().get(0).getHp());
    }

    @Test
    void defend_increases_hp_and_mana() {
        Party a = new Party(0);
        a.addHero(new Hero("A", ClassType.ORDER, 1, 50, 0, 10, 0));
        Party b = new Party(0);
        b.addHero(new Hero("B", ClassType.CHAOS, 1, 40, 0, 0, 0));

        IBattleService engine = new BattleEngine();
        BattleState st = engine.initBattle(a, b);
        engine.applyAction(st, new Action(0,0, ActionType.DEFEND));

        assertEquals(60, a.getHeroes().get(0).getHp());
        assertEquals(5, a.getHeroes().get(0).getMana());
    }
}
