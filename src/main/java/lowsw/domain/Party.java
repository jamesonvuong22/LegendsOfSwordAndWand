package lowsw.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Party {
    public static final int MAX_HEROES = 5;
    private final List<Hero> heroes = new ArrayList<>();
    private int gold;

    public Party(int gold) { this.gold = gold; }
    public List<Hero> getHeroes() { return Collections.unmodifiableList(heroes); }
    public int getGold() { return gold; }
    public boolean isFull() { return heroes.size() >= MAX_HEROES; }
    public void addGold(int amount) { gold += amount; }
    public void spendGold(int amount) {
        if (amount < 0 || gold < amount) throw new IllegalStateException("Not enough gold");
        gold -= amount;
    }
    public void addHero(Hero hero) {
        if (isFull()) throw new IllegalStateException("Party full");
        heroes.add(hero);
    }
    public Hero getHero(int index) { return heroes.get(index); }
    public boolean isDefeated() { return heroes.stream().noneMatch(Hero::isAlive); }
    public int aliveCount() { return (int) heroes.stream().filter(Hero::isAlive).count(); }
}
