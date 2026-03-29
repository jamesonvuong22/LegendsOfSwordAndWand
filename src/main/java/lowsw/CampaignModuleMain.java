package lowsw;

import lowsw.domain.ClassType;
import lowsw.domain.User;
import lowsw.persistence.InMemoryCampaignRepository;
import lowsw.service.CampaignService;
import lowsw.service.DefaultHeroFactory;

public class CampaignModuleMain {
    public static void main(String[] args) {
        System.out.println("=== Campaign Module Demo ===");

        CampaignService campaignService =
                new CampaignService(new InMemoryCampaignRepository(), new DefaultHeroFactory());

        User user = new User(1, "jameson", "HASH::secret");

        var campaign = campaignService.startNew(user, ClassType.WARRIOR);
        System.out.println("Campaign started successfully.");

        campaignService.nextRoom(campaign, "INN");

        System.out.println("Current room: " + campaign.getRoomNumber());
        System.out.println("Last room type: " + campaign.getLastRoomType());
    }
}
