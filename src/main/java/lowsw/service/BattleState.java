package lowsw.service;

import lowsw.domain.Party;

// With the use of AI
public class BattleState {
    private final Party a;
    private final Party b;
    private boolean finished;
    private String result; // "A_WINS" / "B_WINS" / null

    public BattleState(Party a, Party b) {
        this.a = a;
        this.b = b;
        this.finished = false;
        this.result = null;
    }

    public Party getA() { return a; }
    public Party getB() { return b; }
    public boolean isFinished() { return finished; }
    public String getResult() { return result; }

    public void finish(String result) {
        this.finished = true;
        this.result = result;
    }
}
