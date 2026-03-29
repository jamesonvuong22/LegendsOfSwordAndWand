package lowsw;

import lowsw.domain.ClassType;
import lowsw.domain.Party;
import lowsw.persistence.InMemoryPvPRepository;
import lowsw.service.DefaultHeroFactory;
import lowsw.service.PvPService;

public class PvPModuleMain {
    public static void main(String[] args) {
        System.out.println("=== PvP Module Demo ===");

        PvPService pvpService = new PvPService(new InMemoryPvPRepository());

        var invite = pvpService.sendInvitation("jameson", "dave");
        System.out.println("Invitation sent from jameson to dave.");

        pvpService.acceptInvitation(invite.getId());
        System.out.println("Invitation accepted.");

        DefaultHeroFactory factory = new DefaultHeroFactory();

        Party challengerParty = new Party(0);
        challengerParty.addHero(factory.createHero(ClassType.ORDER, "Knight", 1));

        Party opponentParty = new Party(0);
        opponentParty.addHero(factory.createHero(ClassType.CHAOS, "Goblin", 1));

        var state = pvpService.startBattle(challengerParty, opponentParty);

        System.out.println("PvP invite status: " + invite.getStatus());
        System.out.println("PvP battle phase: " + state.getPhase().name());
        System.out.println("PvP battle started successfully.");
    }
}
