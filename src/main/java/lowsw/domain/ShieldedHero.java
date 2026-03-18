package lowsw.domain;
public class ShieldedHero extends HeroDecorator {
    public ShieldedHero(IHero wrappee) { super(wrappee); }
    @Override public int getDefensePower() { return wrappee.getDefensePower() + 5; }
}

