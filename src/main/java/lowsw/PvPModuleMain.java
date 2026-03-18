package lowsw;

import lowsw.domain.ClassType;
import lowsw.domain.Party;
import lowsw.persistence.InMemoryPvPRepository;
import lowsw.service.DefaultHeroFactory;
import lowsw.service.PvPService;

public class PvPModuleMain {
    public static void main(String[] args) {
        PvPService pvpService = new PvPService(new InMemoryPvPRepository());
        var invite = pvpService.sendInvitation("jameson", "dave");
        pvpService.acceptInvitation(invite.getId());

        DefaultHeroFactory factory = new DefaultHeroFactory();
        Party a = new Party(0); a.addHero(factory.createHero(ClassType.ORDER, "A", 1));
        Party b = new Party(0); b.addHero(factory.createHero(ClassType.CHAOS, "B", 1));
        var state = pvpService.startBattle(a, b);
        System.out.println("PvP invite status: " + invite.getStatus());
        System.out.println("PvP battle phase: " + state.getPhase().name());
    }
}

