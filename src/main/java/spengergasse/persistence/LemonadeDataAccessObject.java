package spengergasse.persistence;

import spengergasse.model.Lemonade;
import spengergasse.model.Persistable;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                var expirationDate = resultSet.getString("expirationDate");
                Integer producedNumber = resultSet.getInt("producedNumber");
                if(resultSet.wasNull()){
                    producedNumber=null;
                }
                LocalDate expirDate = LocalDate.parse(expirationDate);
                Lemonade lemonade = new Lemonade(lemonadeName,articleName, expirDate,producedNumber);
                lemonade.setId(id);
                lemonades.add(lemonade);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to execute SELECT for findAll",e);
        }
        return lemonades;

    }

    public Lemonade findOneByArticleNumber(String searchArticleNumber){
        String lemonadeName;
        String articleName;
        String expirationDate;
        Integer producedNumber;
        Long id = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, lemonadeName, articleNumber, expirationDate, producedNumber FROM lemonades WHERE articleNumber=?");
            preparedStatement.setString(1, searchArticleNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

                id = resultSet.getLong("id");
                lemonadeName = resultSet.getString("lemonadeName");
                articleName = resultSet.getString("articleNumber");
                expirationDate = resultSet.getString("expirationDate");
                producedNumber = resultSet.getInt("producedNumber");
                if(resultSet.wasNull()) {
                    producedNumber = null;
                }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to execute SELECT for findOne",e);
        }
        Lemonade lemonade = new Lemonade(lemonadeName, articleName, LocalDate.parse(expirationDate),producedNumber);
        lemonade.setId(id);
        return lemonade;

    }

    public Lemonade save(Lemonade lemonade)
    {
        if(lemonade.isNew()){
            return insert(lemonade);
        }
        return update(lemonade);
    }

    public Lemonade update(Lemonade lemonade) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE lemonades SET lemonadeName= ? , articleNumber= ? , expirationDate= ? , producedNumber = ? " +
                    "WHERE articleNumber=?");
            preparedStatement.setString(1, lemonade.getLemonadeName());
            preparedStatement.setString(2, lemonade.getArticleNumber());
            preparedStatement.setObject(3, lemonade.getExpirationDate());
            preparedStatement.setLong(4, lemonade.getProducedNumber());
            preparedStatement.setString(5, lemonade.getArticleNumber());

            preparedStatement.executeUpdate();

        }
        catch(SQLException e) {
            throw new RuntimeException("Failed update", e);
        }
        return lemonade;
    }

    public Lemonade insert(Lemonade lemonade) {
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

    public Lemonade Delete(Lemonade lemonade){
        return lemonade;
    }
}
