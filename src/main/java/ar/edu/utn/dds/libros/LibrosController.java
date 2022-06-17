package ar.edu.utn.dds.libros;

import io.javalin.http.Context;

import java.io.IOException;

public class LibrosController {

    private RepoLibros repo;
    private QueueFacade queue;

    public LibrosController(RepoLibros repo,QueueFacade queue) {
        this.repo = repo;
        this.queue = queue;
    }

    public void list(Context ctx) {

        String precio_max = ctx.queryParam("precio_max");
        if (precio_max != null) {
            Long precioMax = Long.parseLong(precio_max);
            if (precioMax != null) {
                ctx.json(repo.findByMaxPrecio(precioMax));
            }
        } else {
            ctx.json(repo.findAll());
        }

    }

    public void get(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        ctx.json(repo.findById(id));
    }

    public void delete(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        repo.delete(repo.findById(id));
        ctx.json("deleted");
    }

    public void create(Context ctx) throws IOException {
        //validate
        Libro libro = ctx.bodyAsClass(Libro.class);
        repo.save(libro);
        queue.sendMessage( QueueFacade.CHANNEL_SOLICITAR ,"validarLibro|" + libro.getId().toString());

        ctx.status(201);
    }



}
