import ar.edu.utn.dds.libros.AppLibros;
import ar.edu.utn.dds.libros.Libro;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppLibrosTest {

    static Javalin app;

    @BeforeAll
    public static void init() {
        AppLibros appLibros = new AppLibros();
        appLibros.init();
        app = appLibros.javalinApp();
    }

    @Test
    public void GET_to_fetch_users_returns_list_of_users() {
        Libro libroTest = new Libro();
        libroTest.setNombre("mi plante");
        libroTest.setAutor("Vasconcelos");
        libroTest.setPrecio(67L);

        String libroEnviado = new JavalinJackson().toJsonString(libroTest);
        libroTest.setId(1L);

        String libroRecibido = new JavalinJackson().toJsonString(libroTest);

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.get("/libros").code()).isEqualTo(200);
            client.post("/libros", libroEnviado);
            assertThat(client.get("/libros/1").body().string()).isEqualTo(libroRecibido);
        });
    }

}
