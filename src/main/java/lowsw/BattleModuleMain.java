package lowsw;

import lowsw.controller.BattleController;
import lowsw.domain.Action;
import lowsw.domain.ClassType;
import lowsw.domain.Hero;
import lowsw.domain.Party;
import lowsw.service.ActionType;
import lowsw.service.BasicDamageStrategy;
import lowsw.service.BattleEngine;
import lowsw.service.BattleState;

public class BattleModuleMain {
    public static void main(String[] args) {
        System.out.println("=== Battle Module Demo ===");

        Party player = new Party(500);
        player.addHero(new Hero("Alice", ClassType.ORDER, 1, 50, 20, 12, 4));

        Party enemy = new Party(0);
        enemy.addHero(new Hero("Goblin", ClassType.CHAOS, 1, 40, 10, 8, 2));

        BattleController controller = new BattleController(new BattleEngine(new BasicDamageStrategy()));
        BattleState state = controller.start(player, enemy);

        System.out.println("Battle started.");
        System.out.println("Enemy HP before attack: " + enemy.getHeroes().get(0).getHp());

        controller.act(state, new Action(0, 0, ActionType.ATTACK));
        System.out.println("Enemy HP after attack: " + enemy.getHeroes().get(0).getHp());

        controller.act(state, new Action(0, 0, ActionType.DEFEND));
        System.out.println("Player HP after defend: " + player.getHeroes().get(0).getHp());
        System.out.println("Player Mana after defend: " + player.getHeroes().get(0).getMana());

        String result = state.getResult();
        if (result == null) {
            System.out.println("Battle result: still in progress");
        } else {
            System.out.println("Battle result: " + result);
        }
    }
}
