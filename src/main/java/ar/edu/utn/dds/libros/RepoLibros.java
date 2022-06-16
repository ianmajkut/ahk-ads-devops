package ar.edu.utn.dds.libros;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class RepoLibros {

    private Collection<Libro> libros;

    public RepoLibros() {
        this.libros = new ArrayList<>();
    }

    public void delete(Libro libro) {
        this.libros = this.libros.stream().filter(x -> !x.equals(libro)).collect(Collectors.toList());
    }

    public Long count() {
        return this.libros.stream().count();
    }

    public boolean existsById(Long id) {
        Optional<Libro> first = this.libros.stream().filter(x -> x.getId().equals(id)).findFirst();
        return first.isPresent();

    }

    public Libro findById(Long id) {
        Optional<Libro> first = this.libros.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public Collection<Libro> findByMaxPrecio(Long precioMax ) {
        return this.libros.stream().filter(x -> x.getPrecio()< precioMax ).collect(Collectors.toList());

    }

    public Collection<Libro> find(Long precioMax ) {
        return this.libros.stream().filter(x -> x.getPrecio()< precioMax ).collect(Collectors.toList());

    }

    public Collection<Libro> findAll( ) {
        return new ArrayList<>(this.libros);

    }

    public Libro save(Libro libro) {
        //No es thread safe...
        OptionalLong max = this.libros.stream().mapToLong(x -> x.getId()).max();

        Long nexid = (max.isPresent()) ? (max.getAsLong() + 1L) : 1 ;
        libro.setId(nexid);
        this.libros.add(libro);
        return libro;
    }

}
