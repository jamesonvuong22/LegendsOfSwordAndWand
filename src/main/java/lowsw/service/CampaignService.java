package lowsw.service;

import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.domain.Party;
import lowsw.domain.User;
import lowsw.persistence.CampaignRepository;

public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final HeroFactory heroFactory;

    public CampaignService(CampaignRepository campaignRepository, HeroFactory heroFactory) {
        this.campaignRepository = campaignRepository;
        this.heroFactory = heroFactory;
    }

    public CampaignState startNew(User user, ClassType classType) {
        Party party = new Party(1000);
        party.addHero(heroFactory.createHero(classType, user.getUsername() + "Hero", 1));
        CampaignState state = new CampaignState(user.getId(), user.getUsername(), party, 1, "BATTLE", true);
        campaignRepository.save(state);
        return state;
    }

    public void exit(CampaignState state) {
        state.setInProgress(false);
        campaignRepository.save(state);
    }

    public CampaignState continueCampaign(long userId) {
        return campaignRepository.loadLatest(userId);
    }

    public void nextRoom(CampaignState state, String roomType) {
        state.setRoomNumber(state.getRoomNumber() + 1);
        state.setLastRoomType(roomType);
        state.setInProgress(true);
        campaignRepository.save(state);
    }
}
