package at.technikum.apps.mtcg.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Session;



public class SessionRepository {
    List<Session> sessions;

    private final String FIND_ALL_SQL = "SELECT * FROM sessions";
    private final String SAVE_SQL = "INSERT INTO sessions(id, userId) VALUES(?, ?)";
    private final String DELETE_SQL = "DELETE FROM sessions WHERE id = ?";

    private final Database database = new Database();

    public SessionRepository() {
        this.sessions = new ArrayList<>();
    }

    public List<Session> findAll() {
        this.sessions = new ArrayList<>();

        // recover sessions from database
        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                Session session = new Session(
                        resultSet.getString("id"),
                        resultSet.getString("userId"));
                sessions.add(session);
            }
            return this.sessions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Session> find(String sessionId) {
        Session session = null;
        System.err.println("find session with id " + sessionId);
        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM sessions WHERE id = ?");) {
            statement.setString(1, sessionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                session = new Session(
                        resultSet.getString("id"),
                        resultSet.getString("userId"));
            }
            if (session != null)
                return Optional.ofNullable(session);
            else
                return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Session> save(Session session) {
        // save session to database
        
        /* try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(SAVE_SQL);) {
            statement.setString(1, session.getId());
            statement.setString(2, session.getUserId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(session); */
        return Optional.empty();
    }

    public boolean delete(String sessionId) {
        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL);) {
            statement.setString(1, sessionId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
