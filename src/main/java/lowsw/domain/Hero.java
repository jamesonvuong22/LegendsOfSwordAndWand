package lowsw.domain;
public class Hero implements IHero {
    private final String name;
    private final ClassType classType;
    private int level;
    private int hp;
    private int mana;
    private int attackPower;
    private int defensePower;

    public Hero(String name, ClassType classType, int level, int hp, int mana, int attackPower, int defensePower) {
        this.name = name;
        this.classType = classType;
        this.level = level;
        this.hp = hp;
        this.mana = mana;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
    }

    @Override public String getName() { return name; }
    @Override public ClassType getClassType() { return classType; }
    @Override public int getLevel() { return level; }
    @Override public int getHp() { return hp; }
    @Override public int getMana() { return mana; }
    @Override public int getAttackPower() { return attackPower; }
    @Override public int getDefensePower() { return defensePower; }
    @Override public void setHp(int value) { hp = Math.max(0, value); }
    @Override public void setMana(int value) { mana = Math.max(0, value); }

    public void levelUp() {
        level += 1;
        hp += 10;
        mana += 5;
        attackPower += 2;
        defensePower += 1;
    }

    public int spellCost() { return 10; }
    public int spellDamage() { return getAttackPower() + 8; }

    @Override
    public String toString() {
        return name + "[" + classType + ",L" + level + ",HP=" + hp + ",MP=" + mana + "]";
    }
}
