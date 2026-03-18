package lowsw.domain;
public class CampaignState {
    private final long userId;
    private final String username;
    private final Party party;
    private int roomNumber;
    private String lastRoomType;
    private boolean inProgress;

    public CampaignState(long userId, String username, Party party, int roomNumber, String lastRoomType, boolean inProgress) {
        this.userId = userId;
        this.username = username;
        this.party = party;
        this.roomNumber = roomNumber;
        this.lastRoomType = lastRoomType;
        this.inProgress = inProgress;
    }

    public long getUserId() { return userId; }
    public String getUsername() { return username; }
    public Party getParty() { return party; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public String getLastRoomType() { return lastRoomType; }
    public void setLastRoomType(String lastRoomType) { this.lastRoomType = lastRoomType; }
    public boolean isInProgress() { return inProgress; }
    public void setInProgress(boolean inProgress) { this.inProgress = inProgress; }
}

