package com.educar.actividad2_2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eduardo on 17/12/2015.
 */
public class GestionLibros implements Serializable{

    private ArrayList<Libro> libros;


    public GestionLibros()
    {
        libros = new ArrayList<>();
    }

    /**
     * Metodo que añade libros a la coleccion
     * @param l es el libro a añadir
     */
    public void addLibro(Libro l)
    {
        if(l != null) {
            libros.add(l);
        }
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }


    /**
     * Metodo que busca un libro en funcion del genero,editorial,autor y titulo pasados
     * por parametro
     * @param g es el genero
     * @param e es la editorial
     * @param a es el autor
     * @param t es el titulo
     * @return un libro
     */
    public Libro buscarLibro(String g,String e,String a,String t)
    {
        Libro libro = null;
        for(Libro l:libros)
        {
            if(l.getGenero().equals(g) && l.getEditorial().equals(e) && l.getAutor().equals(a)
                    && l.getTitulo().equals(t))
            {

                return l;
            }
        }
        return libro;
    }


    /**
     * Metodo que obtiene los generos disponibles en la coleccion de libros
     * @return una coleccion con los generos disponibles
     */
    public ArrayList<String> generosDisponibles()
    {
        ArrayList<String> generosDisponibles = new ArrayList<>();
        for (Libro libro: libros)
        {
            if(!generosDisponibles.contains(libro.getGenero()))
            {
                generosDisponibles.add(libro.getGenero());

            }
        }

        return generosDisponibles;

    }

    /**
     * Metodo que que obtiene una coleccion de editoriales que tienen un genero determinado
     * pasado por parametro
     * @param genero es el genero que tienen las editoriales a buscar
     * @return una coleccion de editoriales
     */
    public ArrayList<String> editorialesDisponibles(String genero)
    {
        ArrayList<String> editoriales = new ArrayList<>();
        if (genero != null) {
        for(Libro libro : libros) {

                if (genero.equals(libro.getGenero()) && !editoriales.contains(libro.getEditorial())) {
                    editoriales.add(libro.getEditorial());
                }

        }
        }
        return editoriales;
    }

    /**
     * Metodo que obtiene una coleccion de autores determinados por un genero y editorial
     * pasadas por parametro
     * @param genero es el genero
     * @param editorial es la editorial
     * @return una coleccion de autores
     */
    public ArrayList<String> autoresDisponibles(String genero,String editorial)
    {
        ArrayList<String> autores = new ArrayList<>();


        if(genero != null) {
            if (editorial != null)
            {
                for (Libro libro : libros) {
                    if (libro.getGenero().equals(genero) && libro.getEditorial().equals(editorial)) {
                        if (!autores.contains(libro.getAutor())) {
                            autores.add(libro.getAutor());
                        }
                    }
                }
            }
        }
        return autores;
    }

    /**
     * Metodo que obtiene una coleccion de titulos de libro en funcion de un genero,
     * editorial y autor pasados por parametro
     * @param genero es el genero del libro
     * @param editorial es la editorial del libro
     * @param autor es el autor del libro
     * @return una coleccion de tipo String de titulos de libro
     */
    public ArrayList<String> titulosDisponibles(String genero,String editorial,String autor)
    {
        ArrayList<String> titulosDisponibles = new ArrayList<>();
        if(genero != null) {
            if (editorial != null) {
                if (autor != null) {
                    for (Libro libro : libros) {
                        if (libro.getGenero().equals(genero) && libro.getEditorial().equals(editorial)
                                && libro.getAutor().equals(autor)) {


                            titulosDisponibles.add(libro.getTitulo());

                        }
                    }
                }
            }
        }
        return titulosDisponibles;
    }

    /**
     * Metodo que sustituye un libro por otro que tienen el mismo codigo identificativo
     * @param libro es el libro que va a añadirse
     */
    public void modificarLibro(Libro libro)
    {
        for(int i = 0;i < libros.size();i++)
        {
            if(libros.get(i).getCodLibro() == libro.getCodLibro())
            {
                libros.set(i,libro);
            }
        }
    }

    public void eliminarRegistro(Libro libro)
    {
        for(int i = 0;i < libros.size();i++)
        {
            if(libros.get(i).getCodLibro() == libro.getCodLibro())
            {
                libros.remove(i);
            }
        }
    }


}
