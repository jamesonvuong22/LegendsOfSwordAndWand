package lowsw.controller;

import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.service.InnService;

public class InnController {
    private final InnService innService;
    public InnController(InnService innService) { this.innService = innService; }
    public void rest(CampaignState state) { innService.rest(state); }
    public void buyBread(CampaignState state) { innService.buyBread(state); }
    public void recruit(CampaignState state, ClassType type, String name) { innService.recruit(state, type, name); }
}

