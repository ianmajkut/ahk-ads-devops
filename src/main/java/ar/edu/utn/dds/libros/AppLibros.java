package ar.edu.utn.dds.libros;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class AppLibros {

    private Javalin app;
    private RepoLibros repo;
    private LibrosController controller;
    public Javalin javalinApp() {
        return app;
    }

    public void init(){
        this.app = Javalin.create();
        repo = new RepoLibros();
        controller = new LibrosController(repo);

        app.get(  "/home",  (Context ctx) -> ctx.result("hola!"));

        app.get(  "/libros",  controller::list);

        app.get("/libros/{id}", controller::get);

        app.delete("/libros/{id}", controller::delete);

        app.post("/libros/",controller::create);
    }
    public void start(){
        app.start( getHerokuAssignedPort());
    }

    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7000;
    }

    public static void main(String[] args) {
        AppLibros app = new AppLibros();
        app.init();
        app.start();

    }

}
