package lowsw;

import lowsw.persistence.*;
import lowsw.service.CampaignState;

// With the use of AI
public class CampaignModuleMain {
    public static void main(String[] args) {
        // Minimal DB save/load demo for Deliverable 1
        Db db = new Db(new DbConfig());
        ICampaignRepository repo = new MySqlCampaignRepository(db);

        long userId = 1L;
        long partyId = 1L;

        CampaignState state = new CampaignState(userId, partyId, 1, "BATTLE");
        repo.save(state);
        System.out.println("Saved campaign: room=" + state.getRoomNumber());

        CampaignState loaded = repo.loadLatest(userId);
        System.out.println("Loaded campaign: " + (loaded == null ? "none" : ("room=" + loaded.getRoomNumber() + ", last=" + loaded.getLastRoomType())));
    }
}
