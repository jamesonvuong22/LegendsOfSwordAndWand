package lowsw.persistence;

import lowsw.domain.CampaignState;

// With the use of AI
public interface ICampaignRepository {
    void save(CampaignState state);
    CampaignState loadLatest(long userId);
}
