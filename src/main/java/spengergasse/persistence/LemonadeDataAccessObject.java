package spengergasse.persistence;

import spengergasse.model.Lemonade;
import spengergasse.model.Persistable;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class LemonadeDataAccessObject {

    private final Connection connection;

    public LemonadeDataAccessObject(Connection connection){
        this.connection=connection;
    }

    public List<Lemonade> findAll(){
        List<Lemonade> lemonades = new ArrayList<>();
        try {
            var selectStatement = "SELECT id, lemonadeName, articleNumber, expirationDate, producedNumber FROM lemonades";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                var id = resultSet.getLong("id");
                var lemonadeName = resultSet.getString("lemonadeName");
                var articleName = resultSet.getString("expirationDate");
                var expirationDate = resultSet.getObject("", LocalDate.class);
                Integer producedNumber = resultSet.getInt("producedNumber");
                if(resultSet.wasNull()){
                    producedNumber=null;
                }
                Lemonade lemonade = new Lemonade(lemonadeName,articleName,expirationDate,producedNumber);
                lemonade.setId(id);
                lemonades.add(lemonade);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to execute SELECT for findAll",e);
        }
        return lemonades;

    }

    public Lemonade save(Lemonade lemonade)
    {
        if(lemonade.isNew()){
            return insert(lemonade);
        }
        return update(lemonade);
    }

    private Lemonade update(Lemonade lemonade) {
        return lemonade;
    }

    private Lemonade insert(Lemonade lemonade) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO lemonades (lemonadeName, articleNumber, expirationDate, producedNumber) " +
                    "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, lemonade.getLemonadeName());
            preparedStatement.setString(2, lemonade.getArticleNumber());
            preparedStatement.setObject(3, lemonade.getExpirationDate());
            preparedStatement.setInt(4, lemonade.getProducedNumber());
            int rows = preparedStatement.executeUpdate();
            if (rows!=1){
                throw new RuntimeException("To many rows inserted");
            }
            Long id = null;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while(generatedKeys.next()){
                id = generatedKeys.getLong(1);
            }
            lemonade.setId(id);
        }
        catch (SQLException e){
            throw new RuntimeException("Insert Lemonade failed", e);
        }
        return lemonade;
    }
}
