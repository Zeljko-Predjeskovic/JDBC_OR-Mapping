package spengergasse.presistable;

import com.sun.tools.jconsole.JConsoleContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.descriptor.FileSystemSource;
import spengergasse.model.Lemonade;
import spengergasse.persistence.LemonadeDataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LemonadeDataAccessObjectTest {

    Connection connection;
    private LemonadeDataAccessObject lemonadeDataAccessObject;


    @BeforeEach
    void initializeDB(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS lemonades(id INTEGER PRIMARY KEY AUTOINCREMENT,lemonadeName VARCHAR , articleNumber VARCHAR , expirationDate DATE , producedNumber INTEGER );");
            lemonadeDataAccessObject = new LemonadeDataAccessObject(connection);

        }catch(SQLException e){
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    @AfterEach
    void destroyDB(){
        try {
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException e){
            throw new RuntimeException("Failed destryoing Database",e);
        }
    }

    @Test
    void assertFindAll(){

        List<Lemonade> lemonadeList = lemonadeDataAccessObject.findAll();
        System.out.println(""+lemonadeList);
        Assertions.assertThat(lemonadeList).isNotNull();

    }

    @Test
    void assertFindOne(){
        Assertions.assertThat(lemonadeDataAccessObject.findOneByArticleNumber("647883930").getId()).isEqualTo(2);  }


    @Test
    void assertUpdate(){
        LocalDate expirationDate = LocalDate.of(2000,6,2);
        Lemonade lemonade = new Lemonade("Cola", "647883930", expirationDate,500);
        lemonadeDataAccessObject.update(lemonade);
        Assertions.assertThat(lemonade).isNotNull();

    }

    @Test
    void assertSaveInsert(){
        LocalDate expirationDate = LocalDate.of(2000,6,2);
        Lemonade lemonade = new Lemonade("Cola", "647883930", expirationDate,250);
        lemonadeDataAccessObject.insert(lemonade);
        Assertions.assertThat(lemonade.getId()).isNotNull();
        }

}
