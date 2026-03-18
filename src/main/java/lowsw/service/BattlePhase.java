package lowsw.service;
public interface BattlePhase {
    BattleState handle(BattleState state);
    String name();
}
