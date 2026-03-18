package lowsw;

import lowsw.controller.BattleController;
import lowsw.domain.Action;
import lowsw.domain.ActionType;
import lowsw.domain.ClassType;
import lowsw.domain.Party;
import lowsw.service.BasicDamageStrategy;
import lowsw.service.BattleEngine;
import lowsw.service.BattleState;
import lowsw.service.DefaultHeroFactory;

public class BattleModuleMain {
    public static void main(String[] args) {
        DefaultHeroFactory factory = new DefaultHeroFactory();
        Party player = new Party(500);
        player.addHero(factory.createHero(ClassType.ORDER, "Alice", 1));
        Party enemy = new Party(0);
        enemy.addHero(factory.createHero(ClassType.CHAOS, "Goblin", 1));

        BattleController controller = new BattleController(new BattleEngine(new BasicDamageStrategy()));
        BattleState state = controller.start(player, enemy);
        controller.act(state, new Action(0, 0, ActionType.ATTACK));
        controller.act(state, new Action(0, 0, ActionType.CAST));
        System.out.println("Enemy HP: " + enemy.getHero(0).getHp());
        System.out.println("Battle result: " + state.getResult());
    }
}
