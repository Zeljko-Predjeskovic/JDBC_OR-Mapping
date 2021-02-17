package spengergasse.presistable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spengergasse.model.Lemonade;
import spengergasse.persistence.LemonadeDataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LemonadeDataAccessObjectTest {

    private LemonadeDataAccessObject lemonadeDataAccessObject;


    @BeforeEach
    void initializeDB(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
            connection.createStatement().execute("CREATE TABLE lemonades(id INTEGER PRIMARY KEY ,lemonadeName VARCHAR , articleNumber VARCHAR , expirationDate DATE , producedNumber INTEGER )");
            lemonadeDataAccessObject = new LemonadeDataAccessObject(connection);

        }catch(SQLException e){
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    @Test
    void assertFindAll(){
        List<Lemonade> lemonadeList = lemonadeDataAccessObject.findAll();
        Assertions.assertThat(lemonadeList).isNotNull().isEmpty();
    }

    @Test
    void assertSave(){
        Lemonade lemonade = new Lemonade("Coca Cola", "657883930", LocalDate.now().plusMonths(2),200);
        Lemonade savedLemonade = lemonadeDataAccessObject.save(lemonade);
        Assertions.assertThat(savedLemonade).isEqualTo(lemonade);

    }
}
