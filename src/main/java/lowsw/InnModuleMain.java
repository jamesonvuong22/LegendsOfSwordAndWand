package lowsw;

import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.domain.Party;
import lowsw.service.InnService;

public class InnModuleMain {
    public static void main(String[] args) {
        System.out.println("=== Inn Module Demo ===");

        Party party = new Party(200);
        CampaignState state = new CampaignState(1L, "jameson", party, 2, "INN", true);

        InnService innService = new InnService();

        innService.rest(state);
        System.out.println("Party rested successfully.");

        boolean bought = innService.buyItem(state, "Potion", 25);
        System.out.println("Bought potion: " + bought);

        boolean recruited = innService.recruitHero(state, ClassType.ORDER, "Knight", 100);
        System.out.println("Recruited hero: " + recruited);

        System.out.println("Gold remaining: " + state.getParty().getGold());
        System.out.println("Inventory: " + state.getInventory());
        System.out.println("Party size: " + state.getParty().getHeroes().size());
    }
}
