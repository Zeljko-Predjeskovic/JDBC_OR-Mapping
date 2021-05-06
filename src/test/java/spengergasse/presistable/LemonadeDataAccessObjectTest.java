package spengergasse.presistable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import spengergasse.model.Lemonade;
import spengergasse.persistence.LemonadeDataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    void assertSaveInsert(){
        LocalDate expirationDate = LocalDate.of(2000,6,2);
        Lemonade lemonade = new Lemonade("Cola", "647883930", expirationDate,300);
        lemonadeDataAccessObject.insert(lemonade);
        Assertions.assertThat(lemonade.getId()).isNotNull();
    }

    @Test
    @Order(2)
    void assertFindOne(){
        Assertions.assertThat(lemonadeDataAccessObject.findOneByArticleNumber("647883930").getArticleNumber()).isEqualTo("647883930");  }


    @Test
    @Order(3)
    void assertUpdate(){
        LocalDate expirationDate = LocalDate.of(2000,6,2);
        Lemonade lemonade = new Lemonade("Cola", "647883930", expirationDate,800);
        lemonadeDataAccessObject.update(lemonade);
        Assertions.assertThat(lemonade).isNotNull();
    }

    @Test
    @Order(4)
    void assertFindAll(){
        List<Lemonade> lemonadeList = lemonadeDataAccessObject.findAll();
        System.out.println(""+lemonadeList);
        Assertions.assertThat(lemonadeList).isNotNull();

    }


    @Test
    @Order(5)
    void assertDelete(){
        Assertions.assertThat(lemonadeDataAccessObject.delete("647883930").getArticleNumber()).isEqualTo("647883930");
    }
}
