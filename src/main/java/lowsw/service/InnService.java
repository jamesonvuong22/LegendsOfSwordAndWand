package lowsw.service;

import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.domain.Hero;

public class InnService {
    private final HeroFactory heroFactory;
    public InnService(HeroFactory heroFactory) { this.heroFactory = heroFactory; }

    public void rest(CampaignState state) {
        for (Hero hero : state.getParty().getHeroes()) {
            hero.setHp(hero.getHp() + 20);
            hero.setMana(hero.getMana() + 20);
        }
    }

    public void buyBread(CampaignState state) {
        state.getParty().spendGold(200);
        Hero hero = state.getParty().getHero(0);
        hero.setHp(hero.getHp() + 20);
    }

    public void recruit(CampaignState state, ClassType type, String name) {
        state.getParty().spendGold(600);
        state.getParty().addHero(heroFactory.createHero(type, name, 3));
    }
}
