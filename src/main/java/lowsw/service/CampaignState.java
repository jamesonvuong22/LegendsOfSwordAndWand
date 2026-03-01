package lowsw.service;
// With the use of AI
public class CampaignState {
    private final long userId;
    private final long partyId;
    private int roomNumber;
    private String lastRoomType; // INN/BATTLE/etc.

    public CampaignState(long userId, long partyId, int roomNumber, String lastRoomType) {
        this.userId = userId;
        this.partyId = partyId;
        this.roomNumber = roomNumber;
        this.lastRoomType = lastRoomType;
    }

    public long getUserId() { return userId; }
    public long getPartyId() { return partyId; }
    public int getRoomNumber() { return roomNumber; }
    public String getLastRoomType() { return lastRoomType; }

    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setLastRoomType(String lastRoomType) { this.lastRoomType = lastRoomType; }
}
