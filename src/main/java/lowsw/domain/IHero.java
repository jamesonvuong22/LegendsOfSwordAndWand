package lowsw.domain;
public interface IHero {
    String getName();
    ClassType getClassType();
    int getLevel();
    int getHp();
    int getMana();
    int getAttackPower();
    int getDefensePower();
    void setHp(int value);
    void setMana(int value);
    default boolean isAlive() { return getHp() > 0; }
}

