package lowsw.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lowsw.domain.CampaignState;
import lowsw.domain.Party;

// With the use of AI
public class MySqlCampaignRepository implements ICampaignRepository {
    private final Db db;

    public MySqlCampaignRepository(Db db) {
        this.db = db;
    }

    @Override
    public void save(CampaignState s) {
        String sql = "INSERT INTO campaigns(user_id, room_number, last_room_type, in_progress) " +
                     "VALUES(?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE room_number=VALUES(room_number), " +
                     "last_room_type=VALUES(last_room_type), in_progress=VALUES(in_progress)";

        try (Connection c = db.getConnection(new DbConfig());
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, s.getUserId());
            ps.setInt(2, s.getRoomNumber());
            ps.setString(3, s.getLastRoomType());
            ps.setBoolean(4, s.isInProgress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CampaignState loadLatest(long userId) {
        String sql = "SELECT user_id, room_number, last_room_type, in_progress " +
                     "FROM campaigns WHERE user_id=? ORDER BY user_id DESC LIMIT 1";

        try (Connection c = db.getConnection(new DbConfig());
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return new CampaignState(
                        rs.getLong("user_id"),
                        "loaded-user",
                        new Party(0),
                        rs.getInt("room_number"),
                        rs.getString("last_room_type"),
                        rs.getBoolean("in_progress")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
