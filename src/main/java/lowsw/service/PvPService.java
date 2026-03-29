package lowsw.service;

import lowsw.domain.Party;
import lowsw.domain.PvPInvitation;
import lowsw.persistence.PvPRepository;

public class PvPService {
    private final PvPRepository pvpRepository;

    public PvPService(PvPRepository pvpRepository) {
        this.pvpRepository = pvpRepository;
    }

    public PvPInvitation sendInvitation(String challenger, String opponent) {
        return pvpRepository.createInvite(challenger, opponent);
    }

    public PvPInvitation acceptInvitation(long inviteId) {
        return pvpRepository.accept(inviteId);
    }

    public BattleState startBattle(Party challengerParty, Party opponentParty) {
        return new BattleEngine(new BasicDamageStrategy()).initBattle(challengerParty, opponentParty);
    }
}
