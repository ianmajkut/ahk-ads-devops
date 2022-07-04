import ar.edu.utn.dds.libros.AppLibros;
import ar.edu.utn.dds.libros.Libro;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppLibrosTest {



    @Test
    public void testAlgoQueDeberiaEstarBien() {
        Assertions.assertEquals(1,2);
    }



}
