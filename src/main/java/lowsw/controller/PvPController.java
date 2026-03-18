package lowsw.controller;

import lowsw.domain.Party;
import lowsw.domain.PvPInvitation;
import lowsw.service.BattleState;
import lowsw.service.PvPService;

// With the use of AI
public class PvPController {
    private final PvPService pvpService;
    public PvPController(PvPService pvpService) { this.pvpService = pvpService; }
    public PvPInvitation sendInvite(String challenger, String opponent) { return pvpService.sendInvitation(challenger, opponent); }
    public PvPInvitation acceptInvite(long inviteId) { return pvpService.acceptInvitation(inviteId); }
    public BattleState startBattle(Party challengerParty, Party opponentParty) { return pvpService.startBattle(challengerParty, opponentParty); }
}

