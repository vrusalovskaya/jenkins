package org.example.persistence.daos;

import org.example.persistence.entities.TeamEntity;

import java.sql.SQLException;
import java.util.List;

public class TeamDao extends DaoBase {
    private static final String TEAM_ID_COLUMN_NAME = "id";
    private static final String TEAM_NAME_COLUMN_NAME = "name";
    private static final String TABLE_NAME = "Team";

    private static final String SELECT_FROM_STUDENT_SCRIPT = "SELECT * FROM " + TABLE_NAME;

    public TeamDao(String url, String userName, String password) {
        super(url, userName, password);
    }

    public List<TeamEntity> getTeams() throws SQLException {
        return getList(SELECT_FROM_STUDENT_SCRIPT, result -> {
            int teamId = result.getInt(TEAM_ID_COLUMN_NAME);
            String teamName = result.getString(TEAM_NAME_COLUMN_NAME);
            return new TeamEntity(teamId, teamName);
        });
    }

}
