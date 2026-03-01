package lowsw.domain;
// With the use of AI
public class Hero {
    private final String name;
    private final ClassType classType;
    private int level;
    private int hp;
    private int mana;
    private int atk;
    private int def;

    public Hero(String name, ClassType classType, int level, int hp, int mana, int atk, int def) {
        this.name = name;
        this.classType = classType;
        this.level = level;
        this.hp = hp;
        this.mana = mana;
        this.atk = atk;
        this.def = def;
    }

    public String getName() { return name; }
    public ClassType getClassType() { return classType; }
    public int getLevel() { return level; }
    public int getHp() { return hp; }
    public int getMana() { return mana; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }

    public void setHp(int hp) { this.hp = Math.max(0, hp); }
    public void setMana(int mana) { this.mana = Math.max(0, mana); }

    @Override public String toString() {
        return name + " [" + classType + "] L" + level + " HP=" + hp + " MP=" + mana + " A=" + atk + " D=" + def;
    }
}
