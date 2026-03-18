package lowsw.persistence;

import java.util.HashMap;
import java.util.Map;

import lowsw.domain.CampaignState;

public class InMemoryCampaignRepository implements CampaignRepository {
    private final Map<Long, CampaignState> campaigns = new HashMap<>();
    @Override public void save(CampaignState state) { campaigns.put(state.getUserId(), state); }
    @Override public CampaignState loadLatest(long userId) { return campaigns.get(userId); }
}
