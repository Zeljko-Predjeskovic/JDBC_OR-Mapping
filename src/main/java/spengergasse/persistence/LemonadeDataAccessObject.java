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


public class LemonadeDataAccessObject {

    private final Connection connection;

    public LemonadeDataAccessObject(Connection connection){
        this.connection=connection;
    }

    public List<Lemonade> findAll(){
        List<Lemonade> lemonades = new ArrayList<>();
        try {
            var selectStatement = "SELECT lemonadeName, articleNumber, expirationDate, producedNumber FROM lemonades";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                var lemonadeName = resultSet.getString("lemonadeName");
                var articleName = resultSet.getString("expirationDate");
                var expirationDate = resultSet.getObject("", LocalDate.class);
                Integer producedNumber = resultSet.getInt("producedNumber");
                if(resultSet.wasNull()){
                    producedNumber=null;
                }
                lemonades.add(new Lemonade(lemonadeName,articleName,expirationDate,producedNumber));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to execute SELECT for findAll",e);
        }
        return lemonades;

    }

    public Lemonade save(Lemonade lemondade)
    {
        return lemondade;
    }
}
