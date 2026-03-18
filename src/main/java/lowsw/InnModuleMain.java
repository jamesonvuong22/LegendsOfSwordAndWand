package lowsw;

import lowsw.domain.ClassType;
import lowsw.domain.User;
import lowsw.persistence.InMemoryCampaignRepository;
import lowsw.service.CampaignService;
import lowsw.service.DefaultHeroFactory;
import lowsw.service.InnService;

// With the use of AI
public class InnModuleMain {
    public static void main(String[] args) {
        DefaultHeroFactory factory = new DefaultHeroFactory();
        CampaignService campaignService = new CampaignService(new InMemoryCampaignRepository(), factory);
        var campaign = campaignService.startNew(new User(1, "dave", "HASH::pw"), ClassType.MAGE);
        InnService innService = new InnService(factory);
        innService.rest(campaign);
        innService.buyBread(campaign);
        innService.recruit(campaign, ClassType.ORDER, "Knight");
        System.out.println("Party size after inn: " + campaign.getParty().getHeroes().size());
    }
}

