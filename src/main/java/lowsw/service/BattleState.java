package lowsw.service;

import lowsw.domain.Party;

public class BattleState {
    private final Party playerParty;
    private final Party enemyParty;
    private boolean finished;
    private String result;
    private BattlePhase phase;

    public BattleState(Party playerParty, Party enemyParty) {
        this.playerParty = playerParty;
        this.enemyParty = enemyParty;
        this.phase = new ActiveBattlePhase();
    }

    public Party getPlayerParty() { return playerParty; }
    public Party getEnemyParty() { return enemyParty; }
    public boolean isFinished() { return finished; }
    public String getResult() { return result; }
    public BattlePhase getPhase() { return phase; }
    public void finish(String result) { this.finished = true; this.result = result; this.phase = new FinishedBattlePhase(); }
}
