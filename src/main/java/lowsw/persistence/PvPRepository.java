package lowsw.persistence;

import lowsw.domain.PvPInvitation;

public interface PvPRepository {
    PvPInvitation createInvite(String challenger, String opponent);
    PvPInvitation accept(long inviteId);
}
