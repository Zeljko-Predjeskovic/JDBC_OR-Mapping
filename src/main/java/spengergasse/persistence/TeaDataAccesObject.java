package spengergasse.persistence;

import spengergasse.model.Lemonade;
import spengergasse.model.Persistable;
import spengergasse.model.Tea;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    @Override
    protected String updateStatement() {
        return "UPDATE teas SET teeName= ? , caffeinInMiligramm= ? WHERE id=?";
    }

    @Override
    protected void bindPersistableUpdate(PreparedStatement preparedStatement, Object persistable) {
        if (persistable instanceof Tea ) {
            Tea tea = (Tea) persistable;
            try{
                preparedStatement.setString(1, tea.getTeeName());
                preparedStatement.setInt(2,tea.getCaffeinInMilligramm());

            } catch (Exception e){
                throw new RuntimeException("Failed to bind tea into update!! " , e);
            }
        }
        else {
            throw new IllegalArgumentException("Cannot bind Tea because persistable is not Tea");
        }
    }


}
