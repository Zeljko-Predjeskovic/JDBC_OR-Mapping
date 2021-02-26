package spengergasse.persistence;

import spengergasse.model.Lemonade;
import spengergasse.model.Persistable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataAccessObject <T extends Persistable> {

    private final Connection connection;

    public AbstractDataAccessObject(Connection connection){
        this.connection=connection;
    }

    public List<T> findAll(){
        List<T> persistables = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                persistables.add(mapResultSetToPersistable(resultSet));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to execute SELECT for findAll",e);
        }
        return persistables;

    }

    protected abstract String selectStatement();

    protected abstract T mapResultSetToPersistable(ResultSet resultSet);


}
