package lowsw.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// With the use of AI
public class Party {
    public static final int MAX_HEROES = 5;

    private final List<Hero> heroes = new ArrayList<>();
    private int gold;

    public Party(int gold) { this.gold = gold; }

    public List<Hero> getHeroes() { return Collections.unmodifiableList(heroes); }
    public int getGold() { return gold; }

    public boolean isFull() { return heroes.size() >= MAX_HEROES; }

    public void addHero(Hero h) {
        if (isFull()) throw new IllegalStateException("Party already has 5 heroes");
        heroes.add(h);
    }

    public void addGold(int amount) { gold += amount; }
    public void spendGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount");
        if (gold < amount) throw new IllegalStateException("Not enough gold");
        gold -= amount;
    }
}
