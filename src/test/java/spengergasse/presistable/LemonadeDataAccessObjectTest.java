package spengergasse.presistable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spengergasse.model.Lemonade;
import spengergasse.persistence.LemonadeDataAccessObject;

import java.util.List;

public class LemonadeDataAccessObjectTest {

    private LemonadeDataAccessObject lemonadeDataAccessObject;

    @Test
    void assertFindAll(){
        List<Lemonade> lemonadeList = lemonadeDataAccessObject.findAll();
        Assertions.assertThat(lemonadeList).isNotNull().isEmpty();
    }
}
