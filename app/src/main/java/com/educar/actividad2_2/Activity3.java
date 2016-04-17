package com.educar.actividad2_2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity3 extends AppCompatActivity {

    private TextView tituloLibro;
    private TextView codigoLibro;
    private TextView autorLibro;
    private TextView editorialLibro;
    private TextView generoLibro;
    private TextView disponibleLibro;
    private Button back;
    private Button prestamo;
    //booleano que indicara si se ha realizado o no el prestamo del libro
    private  boolean prestamoSolicitado;
    private TextView inicioPrestamo;
    private TextView finPrestamo;
    private Button delete;
    private Libro libro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        tituloLibro = (TextView) findViewById(R.id.tv_libroTitulo);
        codigoLibro = (TextView) findViewById(R.id.tv_libroCodigo);
        autorLibro = (TextView) findViewById(R.id.tv_libroAutor);
        editorialLibro = (TextView) findViewById(R.id.tv_libroEditorial);
        generoLibro = (TextView) findViewById(R.id.tv_libroGenero);
        disponibleLibro=(TextView)findViewById(R.id.tv_libroDisponible);
        back =(Button)findViewById(R.id.bt_back);
        prestamo=(Button)findViewById(R.id.bt_prestamo);
        inicioPrestamo=(TextView)findViewById(R.id.tv_fechIniPres);
        finPrestamo=(TextView)findViewById(R.id.tv_fechaFinPres);
        delete =(Button)findViewById(R.id.bt_eliminar);
        //por defecto siempre se inicializa este boolean como false,cuando se hace el prestamo cambia a true
        prestamoSolicitado = false;

        libro = (Libro) getIntent().getSerializableExtra("libro");
        //a partir de el objeto libro pasado a esta activity se setean los textview
        tituloLibro.setText(libro.getTitulo());
        codigoLibro.setText(String.valueOf(libro.getCodLibro()));
        autorLibro.setText(libro.getAutor());
        editorialLibro.setText(libro.getEditorial());
        generoLibro.setText(libro.getGenero());
        disponibleLibro.setText(libro.isDisponible()? "Si" : "No");
        //en el caso de que no haya sido prestado el libro(no tiene ninguna fecha asocidad) se dejara el texView en blanco
        if(libro.getInicioPres() == null)
        {
            inicioPrestamo.setText("");
        }
        else
        {
            inicioPrestamo.setText(calendarToString(libro.getInicioPres()));
        }
        if(libro.getFinPres() == null)
        {
            finPrestamo.setText("");
        }
        else
        {
            finPrestamo.setText(calendarToString(libro.getFinPres()));
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si se ha realizado el prestamo al usar el boton volver se le retornara el objeto
                //modificado a al activity que lanzó la activity en la que estamos
                if (prestamoSolicitado) {
                    Intent dataIntent = new Intent();
                    dataIntent.putExtra("libroModi", libro);
                    setResult(RESULT_OK, dataIntent);

                    finish();
                } else {
                    setResult(RESULT_CANCELED);

                    finish();
                }

            }
        });

        prestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(libro.isDisponible())
                {
                    libro.setDisponible(false);
                    //se pone como fecha de inicio de prestamo la fecha actual
                    libro.setInicioPres(Calendar.getInstance());
                    Calendar fecha = Calendar.getInstance();
                    //se pone como fecha de fin de prestamo 15 dias despues de la fecha actual
                    fecha.add(Calendar.DAY_OF_YEAR,15);
                    libro.setFinPres(fecha);
                    //se setean los valores modificados con el prestamo realizado
                    disponibleLibro.setText(libro.isDisponible() ? "Si" : "No");
                    prestamoSolicitado = true;
                    inicioPrestamo.setText(calendarToString(libro.getInicioPres()));
                    finPrestamo.setText(calendarToString(libro.getFinPres()));
                }
                else
                {

                    Toast toast = new Toast(getApplicationContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast,
                            (ViewGroup) findViewById(R.id.lytLayout));
                    TextView txtMsg = (TextView)layout.findViewById(R.id.tv_toastMsg);
                    txtMsg.setText(R.string.msjNoPrestamo);

                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    //toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("libroBorrado",libro);
                setResult(Activity2.RESULT_DELETE, intent);
                finish();
            }
        });

    }

    /**
     * Metodo que pasa una fecha en formato Calendar a String
     * @param cal es la fecha a transformar
     * @return una fecha en formato cadena
     */
    private String calendarToString(Calendar cal)
    {
        String fecha ="";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(cal != null)
        {
            fecha =  sdf.format(cal.getTime());
        }
        return fecha;
    }


}
