package lowsw.controller;

import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.service.InnService;

public class InnController {
    private final InnService innService;

    public InnController(InnService innService) {
        this.innService = innService;
    }

    public void rest(CampaignState state) {
        innService.rest(state);
    }

    public boolean buyItem(CampaignState state, String itemName, int cost) {
        return innService.buyItem(state, itemName, cost);
    }

    public boolean useItem(CampaignState state, String itemName, int heroIndex) {
        return innService.useItem(state, itemName, heroIndex);
    }

    public boolean recruitHero(CampaignState state, ClassType type, String name, int cost) {
        return innService.recruitHero(state, type, name, cost);
    }
}
