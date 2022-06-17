package ar.edu.utn.dds.libros;

import com.rabbitmq.client.DeliverCallback;
import io.javalin.Javalin;

public class AppLibros {

    private Javalin app;
    private RepoLibros repo;
    private LibrosController controller;
    private QueueFacade queue;


    public Javalin javalinApp() {
        return app;
    }

    public void init(QueueFacade queue) throws Exception {

        this.app = Javalin.create();
        this.queue = queue;
        repo = new RepoLibros();

        controller = new LibrosController(repo,queue);
        app.get(  "/libros",  controller::list);

        app.get("/libros/{id}", controller::get);

        app.delete("/libros/{id}", controller::delete);

        app.post("/libros/",controller::create);


        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            if (message.startsWith("libroValidado|")){
                String[] split = message.split("\\|");
                Libro byId = repo.findById(Long.parseLong(split[1]));
                byId.setValid(true);
            }

        };
        queue.addCallback(deliverCallback1);

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

    public static void main(String[] args) throws Exception {
        AppLibros app = new AppLibros();
        QueueFacade queue = new QueueFacade();
        queue.init(System.getenv("QUEUE_URL"));
        queue.createChanel();

        app.init(queue);



        app.start();

    }

}
