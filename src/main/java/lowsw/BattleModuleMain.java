package lowsw;

import lowsw.controller.BattleController;
import lowsw.domain.*;
import lowsw.service.*;

// With the use of AI
public class BattleModuleMain {
    public static void main(String[] args) {
        Party a = new Party(0);
        a.addHero(new Hero("Alice", ClassType.ORDER, 1, 50, 20, 12, 4));
        Party b = new Party(0);
        b.addHero(new Hero("Goblin", ClassType.CHAOS, 1, 40, 0, 10, 2));

        BattleController bc = new BattleController(new BattleEngine());
        BattleState st = bc.start(a, b);

        System.out.println("== Battle Start ==");
        System.out.println("A: " + a.getHeroes());
        System.out.println("B: " + b.getHeroes());

        st = bc.act(st, new Action(0, 0, ActionType.ATTACK));
        System.out.println("After attack, B: " + b.getHeroes());

        st = bc.act(st, new Action(0, 0, ActionType.ATTACK));
        System.out.println("After attack, B: " + b.getHeroes());
        System.out.println("Finished? " + st.isFinished() + " Result=" + st.getResult());
    }
}
