package ar.edu.utn.dds.libros;

import io.javalin.http.Context;

public class LibrosController {

    private RepoLibros repo;

    public LibrosController(RepoLibros repo) {
        this.repo = repo;
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

    public void create(Context ctx) {
        Libro libro = ctx.bodyAsClass(Libro.class);
        repo.save(libro);
        ctx.json(libro);
        ctx.status(201);
    }
}
