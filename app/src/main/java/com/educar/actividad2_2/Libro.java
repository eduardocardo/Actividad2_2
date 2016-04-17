package com.educar.actividad2_2;

import java.io.Serializable;
import java.util.Calendar;


/**
 * Created by Alumno on 15/12/2015.
 */
public class Libro implements Serializable{

    //indica el titulo del libro
    private String titulo;
    //indica la editorial del libro
    private String editorial;
    //indica el genero
    private String genero;
    //codigo identificador del libro
    private  int codLibro;
    //indica el autor dle libro
    private String autor;
    //indica si el libro esta disponible
    private boolean disponible;
    //codigo numerico que representa la imagen del libro
    private int codImage;
    //fecha de inicio de prestamo del libro
    private Calendar inicioPres;
    //fecha de fin de prestamos
    private Calendar finPres;

    //coleccion que contiene los generos que puede tener un libro
    public static final String[] GENERO ={"Accion y aventuras","Arte y cine","Biografia","Ciencia y medicina",
    "Fantasia y ciencia ficcion","Historia","Infantil y juvenil","Policiaca","Romantica"};

    public static int CODIGO = 1;

    /**ç
     * Constructor de la clase Libro
     * @param t es el titulo del libro
     * @param e es la editorial
     * @param g es el genero del libro
     */
    public Libro(String t,String e,String g,String a)
    {
        titulo = t;
        editorial = e;
        genero = g;
        codLibro =CODIGO++;
        autor = a;
        disponible = true;

    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getGenero() {
        return genero;
    }

    public int getCodLibro() {
        return codLibro;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getCodImage() {
        return codImage;
    }

    public void setCodImage(int codImage) {
        this.codImage = codImage;
    }

    public Calendar getFinPres() {
        return finPres;
    }

    public void setFinPres(Calendar finPres) {
        this.finPres = finPres;
    }

    public Calendar getInicioPres() {
        return inicioPres;
    }

    public void setInicioPres(Calendar inicioPres) {
        this.inicioPres = inicioPres;
    }

    /**
     * Metodo que devuelve un numero entero en funcion del genero que tenga el libro
     * @return un indice en funcion del genero del libro
     */
    public int mostrarIndice()
    {
        int indice = -1;
        if(genero.equals(GENERO[0]))
        {
            indice = 0;
        }
        else if(genero.equals(GENERO[1]))
        {
            indice = 1;
        }
        else if(genero.equals(GENERO[2]))
        {
            indice = 2;
        }
        else if(genero.equals(GENERO[3]))
        {
            indice = 3;
        }
        else if(genero.equals(GENERO[4]))
        {
            indice = 4;
        }
        else if(genero.equals(GENERO[5]))
        {
            indice = 5;
        }
        else if(genero.equals(GENERO[6]))
        {
            indice = 6;
        }
        else if(genero.equals(GENERO[7]))
        {
            indice = 7;
        }
        else if(genero.equals(GENERO[8]))
        {
            indice = 8;
        }

        return indice;
    }


    /**
     * Metodo static para resetear el codigo de la clase Libro
     */
    public static void resetCodigo()
    {
        CODIGO = 1;
    }


}
