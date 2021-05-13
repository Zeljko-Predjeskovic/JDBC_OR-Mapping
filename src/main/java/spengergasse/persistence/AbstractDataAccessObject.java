package spengergasse.persistence;

import spengergasse.model.Lemonade;
import spengergasse.model.Persistable;
import spengergasse.model.Tea;

import java.sql.*;
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


    public T update(T persistable) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement(), Statement.RETURN_GENERATED_KEYS);
            bindPersistableUpdate(preparedStatement, persistable);
            int rows = preparedStatement.executeUpdate();
            if(rows != 1){
                throw new RuntimeException("Too many rows, update stopped");
            }

        }
        catch(SQLException e) {
            throw new RuntimeException("Failed update", e);
        }
        return persistable;
    }

    protected abstract String updateStatement();

    protected abstract void bindPersistableUpdate(PreparedStatement preparedStatement, Object persistable);

    public T insert(T persistable) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement(), Statement.RETURN_GENERATED_KEYS);
            bindPersistableInsert(preparedStatement, persistable);
            int rows = preparedStatement.executeUpdate();
            if (rows!=1){
                throw new RuntimeException("To many rows inserted");
            }
            Long id = null;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while(generatedKeys.next()){
                id = generatedKeys.getLong(1);
            }
            persistable.setId(id);
        }
        catch (SQLException e){
            throw new RuntimeException("Insert Lemonade failed", e);
        }
        return persistable;
    }

    public abstract String insertStatement();

    public abstract void bindPersistableInsert(PreparedStatement preparedStatement, Object persistable);

}
