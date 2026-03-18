package lowsw.service;

import lowsw.domain.IHero;

public interface DamageStrategy {
    int computeDamage(IHero attacker, IHero defender);
}
