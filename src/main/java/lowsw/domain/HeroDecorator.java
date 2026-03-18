package lowsw.domain;
public abstract class HeroDecorator implements IHero {
    protected final IHero wrappee;
    protected HeroDecorator(IHero wrappee) { this.wrappee = wrappee; }
    @Override public String getName() { return wrappee.getName(); }
    @Override public ClassType getClassType() { return wrappee.getClassType(); }
    @Override public int getLevel() { return wrappee.getLevel(); }
    @Override public int getHp() { return wrappee.getHp(); }
    @Override public int getMana() { return wrappee.getMana(); }
    @Override public int getAttackPower() { return wrappee.getAttackPower(); }
    @Override public int getDefensePower() { return wrappee.getDefensePower(); }
    @Override public void setHp(int value) { wrappee.setHp(value); }
    @Override public void setMana(int value) { wrappee.setMana(value); }
}
