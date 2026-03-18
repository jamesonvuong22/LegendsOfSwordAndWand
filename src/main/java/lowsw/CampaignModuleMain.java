package lowsw;

import lowsw.domain.ClassType;
import lowsw.domain.User;
import lowsw.persistence.InMemoryCampaignRepository;
import lowsw.service.CampaignService;
import lowsw.service.DefaultHeroFactory;

public class CampaignModuleMain {
    public static void main(String[] args) {
        CampaignService campaignService = new CampaignService(new InMemoryCampaignRepository(), new DefaultHeroFactory());
        User user = new User(1, "jameson", "HASH::secret");
        var campaign = campaignService.startNew(user, ClassType.WARRIOR);
        campaignService.nextRoom(campaign, "INN");
        System.out.println("Room: " + campaign.getRoomNumber() + ", last room=" + campaign.getLastRoomType());
    }
}
