package spengergasse.presistable;

import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import spengergasse.model.Tea;
import spengergasse.persistence.TeaDataAccesObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeaDataAccessObjectTest {

    Connection connection;
    private TeaDataAccesObject teaDataAccessObject;
    private static Long id;

    @BeforeEach
    void initializeDB(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS teas(id INTEGER PRIMARY KEY AUTOINCREMENT,teeName VARCHAR, caffeinInMiligramm INTEGER );");
            teaDataAccessObject = new TeaDataAccesObject(connection);

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
    void assertTeaFindAll(){
       List<Tea> teas = teaDataAccessObject.findAll();
       Assertions.assertThat(teas).isNotNull();
    }

    @Test
    @Order(2)
    void assertTeaInsert(){
        Tea tea = new Tea("Grüntee",10);

        Tea erg = (Tea) teaDataAccessObject.insert(tea);

        id = tea.getId();

        Assertions.assertThat(erg).isNotNull();
    }

    @Test
    @Order(3)
    void assertTeaUpdate(){
        Tea tea = new Tea("Grüntee",10);
        tea.setId(id);
        Tea erg = (Tea) teaDataAccessObject.update(tea);

        Assertions.assertThat(erg).isNotNull();
    }

    @Test
    @Order(4)
    void assertTeaDelete(){
        Tea tea = new Tea("Grüntee",10);
        tea.setId(id);

        Tea erg = (Tea) teaDataAccessObject.delete(tea);

        Assertions.assertThat(erg).isNotNull();

    }

}
