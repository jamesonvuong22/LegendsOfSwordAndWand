package lowsw.domain;
public class PvPInvitation {
    private final long id;
    private final String challenger;
    private final String opponent;
    private String status;
    public PvPInvitation(long id, String challenger, String opponent, String status) {
        this.id = id;
        this.challenger = challenger;
        this.opponent = opponent;
        this.status = status;
    }
    public long getId() { return id; }
    public String getChallenger() { return challenger; }
    public String getOpponent() { return opponent; }
    public String getStatus() { return status; }
    public void accept() { this.status = "ACCEPTED"; }
}

