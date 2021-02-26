package spengergasse.persistence;

import spengergasse.model.Lemonade;
import spengergasse.model.Persistable;
import spengergasse.model.Tea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TeaDataAccesObject extends AbstractDataAccessObject{


    public TeaDataAccesObject(Connection connection) {
        super(connection);
    }

    @Override
    protected String selectStatement() {
        return "SELECT id, teeName, caffeinInMiligramm FROM teas" ;
    }

    @Override
    protected Tea mapResultSetToPersistable(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String teaName = resultSet.getString("teeName");
            Integer caffeinInMiligramm = resultSet.getInt("caffeinInMiligramm");

            Tea tea = new Tea(teaName,caffeinInMiligramm);
            tea.setId(id);
            return tea;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to map Resultset to Tea", e);
        }
    }

}
