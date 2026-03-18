package lowsw.service;

import lowsw.domain.ClassType;
import lowsw.domain.Hero;

public interface HeroFactory {
    Hero createHero(ClassType type, String name, int level);
}
