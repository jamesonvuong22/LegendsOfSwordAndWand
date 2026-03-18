package lowsw.controller;

import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.domain.User;
import lowsw.service.CampaignService;

public class CampaignController {
    private final CampaignService campaignService;
    public CampaignController(CampaignService campaignService) { this.campaignService = campaignService; }
    public CampaignState startNew(User user, ClassType classType) { return campaignService.startNew(user, classType); }
    public CampaignState continueCampaign(long userId) { return campaignService.continueCampaign(userId); }
    public void exit(CampaignState state) { campaignService.exit(state); }
}

