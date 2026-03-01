package lowsw.persistence;

import lowsw.service.CampaignState;

import java.sql.*;

// With the use of AI
public class MySqlCampaignRepository implements ICampaignRepository {
    private final Db db;

    public MySqlCampaignRepository(Db db) { this.db = db; }

    @Override
    public void save(CampaignState s) {
        String sql = "INSERT INTO campaigns(user_id, party_id, room_number, last_room_type, in_progress) " +
                     "VALUES(?,?,?,?,TRUE) " +
                     "ON DUPLICATE KEY UPDATE room_number=VALUES(room_number), last_room_type=VALUES(last_room_type), in_progress=TRUE";
        // Note: This assumes a UNIQUE constraint on (user_id). If you allow multiple campaigns, refactor schema accordingly.
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, s.getUserId());
            ps.setLong(2, s.getPartyId());
            ps.setInt(3, s.getRoomNumber());
            ps.setString(4, s.getLastRoomType());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CampaignState loadLatest(long userId) {
        String sql = "SELECT user_id, party_id, room_number, last_room_type FROM campaigns WHERE user_id=? AND in_progress=TRUE";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new CampaignState(
                        rs.getLong("user_id"),
                        rs.getLong("party_id"),
                        rs.getInt("room_number"),
                        rs.getString("last_room_type")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
