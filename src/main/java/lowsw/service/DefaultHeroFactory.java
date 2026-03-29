package lowsw.service;

import lowsw.domain.ClassType;
import lowsw.domain.Hero;

public class DefaultHeroFactory implements HeroFactory {
    @Override
    public Hero createHero(ClassType classType, String name, int level) {
        switch (classType) {
            case ORDER:
                return new Hero(name, classType, level, 60, 30, 12, 6);
            case CHAOS:
                return new Hero(name, classType, level, 55, 35, 14, 4);
            case WARRIOR:
                return new Hero(name, classType, level, 70, 15, 16, 8);
            case MAGE:
                return new Hero(name, classType, level, 45, 50, 18, 3);
            default:
                return new Hero(name, classType, level, 50, 20, 10, 5);
        }
    }
}
