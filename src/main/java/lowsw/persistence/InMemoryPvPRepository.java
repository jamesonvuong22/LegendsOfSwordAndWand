package lowsw.persistence;

import java.util.HashMap;
import java.util.Map;

import lowsw.domain.PvPInvitation;

public class InMemoryPvPRepository implements PvPRepository {
    private final Map<Long, PvPInvitation> invitations = new HashMap<>();
    private long nextId = 1;
    @Override
    public PvPInvitation createInvite(String challenger, String opponent) {
        PvPInvitation invite = new PvPInvitation(nextId++, challenger, opponent, "PENDING");
        invitations.put(invite.getId(), invite);
        return invite;
    }
    @Override
    public PvPInvitation accept(long inviteId) {
        PvPInvitation invitation = invitations.get(inviteId);
        if (invitation == null) throw new IllegalStateException("Invitation not found");
        invitation.accept();
        return invitation;
    }
}
