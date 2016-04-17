package com.educar.actividad2_2;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    private EditText titulo;
    private EditText editorial;
    private EditText autor;
    private Spinner genero;
    private Button add;

    private Spinner generoBuscado;
    private Spinner editorialBuscada;
    private Spinner autorBuscado;
    private Spinner tituloBuscado;
    private Button lupa;
    private Button lista;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;

    private GestionLibros gestionLibros;

    public static final int REQUEST_ACTIVI3 = 1;
    public static final int REQUEST_ACTIVI4 = 2;
    public static final int RESULT_DELETE = 5;


    public static final int[] IMAGEN_LIBROS = {R.drawable.aventuras, R.drawable.arte, R.drawable.biografia, R.drawable.ciencia,
            R.drawable.ficcion, R.drawable.history, R.drawable.infantil, R.drawable.policiaca, R.drawable.romantica};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        titulo = (EditText) findViewById(R.id.et_titulo);
        editorial = (EditText) findViewById(R.id.et_editorial);
        autor = (EditText) findViewById(R.id.et_autor);
        genero = (Spinner) findViewById(R.id.sp_genero);
        add = (Button) findViewById(R.id.bt_add);
        generoBuscado = (Spinner) findViewById(R.id.sp_generoDisponibles);
        editorialBuscada = (Spinner) findViewById(R.id.sp_editorialesDisponibles);
        autorBuscado = (Spinner) findViewById(R.id.sp_autoresDisponibles);
        tituloBuscado = (Spinner) findViewById(R.id.sp_titulosDisponibles);


        lupa = (Button) findViewById(R.id.bt_lupa);
        lista = (Button) findViewById(R.id.bt_lista);
        lupa.setEnabled(false);
        lista.setEnabled(false);


        gestionLibros = new GestionLibros();
        //este metodo lo aplico porque genero el código de cada libro de manera secuencial a partir de un atributo
        //static de la clase libro.El problema esta en que si añado libros(se va aumentando el atributo static) y
        //salgo de la app con el boton back,al volver a entrar en la app y  añadir otro libro se mantiene el valor del
        //atributo static que tenia antes.No se si será la forma mas acertada de hacerlo
        Libro.resetCodigo();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se comprueba  si los campos tienen datos
                if (titulo.getText().length() > 0) {
                    if (editorial.getText().length() > 0) {
                        if (autor.getText().length() > 0) {
                            Libro libro = new Libro(titulo.getText().toString(), editorial.getText().toString(),
                                    genero.getSelectedItem().toString(), autor.getText().toString());
                            //se le asigna una imagen al libro en funcion de su genero
                            libro.setCodImage(IMAGEN_LIBROS[libro.mostrarIndice()]);
                            //se añade el libro a la coleccion
                            gestionLibros.addLibro(libro);
                            //se limpian los campos para añadir mas libros
                            titulo.setText("");
                            editorial.setText("");
                            autor.setText("");
                            genero.setSelection(0);
                            //se inicializa y asigna el adapter del primer campo de busqueda
                            adapter1 = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_spinner_item,
                                    gestionLibros.generosDisponibles());
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            generoBuscado.setAdapter(adapter1);
                            //se activa el boton de busqueda y listado
                            lupa.setEnabled(true);
                            lupa.setAlpha(1);
                            lista.setEnabled(true);
                            lista.setAlpha(1);
                        } else {
                            Toast.makeText(Activity2.this, R.string.msjErrorAutor, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Activity2.this, R.string.msjErrorEditorial, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity2.this, R.string.msjErrorTitulo, Toast.LENGTH_SHORT).show();
                }


            }
        });


        //evento sobre el spinner generoBuscado que al seleccionar un genero inicializará un adapter de editoriales
        //que tengan ese genero
        generoBuscado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter2 = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_spinner_item,
                        gestionLibros.editorialesDisponibles(generoBuscado.getSelectedItem().toString()));
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editorialBuscada.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //evento sobre el spinner editorialBuscada que al seleccionar una editorial inicializará un adapter de autores
        //que tengan esa editorial
        editorialBuscada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter3 = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_spinner_item,
                        gestionLibros.autoresDisponibles(generoBuscado.getSelectedItem().toString()
                                , editorialBuscada.getSelectedItem().toString()));
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                autorBuscado.setAdapter(adapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //evento sobre el spinner autorBuscado que al seleccionar un autor inicializará un adapter de titulos
        //que tengan ese autor
        autorBuscado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter4 = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_spinner_item,
                        gestionLibros.titulosDisponibles(generoBuscado.getSelectedItem().toString()
                                , editorialBuscada.getSelectedItem().toString(), autorBuscado.getSelectedItem().toString()));
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tituloBuscado.setAdapter(adapter4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Libro libro = gestionLibros.buscarLibro(generoBuscado.getSelectedItem().toString()
                        , editorialBuscada.getSelectedItem().toString(), autorBuscado.getSelectedItem().toString()
                        , tituloBuscado.getSelectedItem().toString());

                Intent intent = new Intent(Activity2.this, Activity3.class);
                intent.putExtra("libro", libro);
                startActivityForResult(intent, REQUEST_ACTIVI3);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });


        //evento que al pulsar el boton lista inicia una activity con un listview con todos los libros
        //disponibles
        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity2.this, Activity4.class);
                intent.putExtra("gestion", gestionLibros);
                startActivityForResult(intent, REQUEST_ACTIVI4);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACTIVI3:
                if (resultCode == RESULT_OK) {
                    Libro libroModificado = (Libro) data.getSerializableExtra("libroModi");
                    gestionLibros.modificarLibro(libroModificado);
                } else if (resultCode == RESULT_DELETE) {
                    Libro libroAEliminar = (Libro) data.getSerializableExtra("libroBorrado");
                    gestionLibros.eliminarRegistro(libroAEliminar);

                    //se inicializa y asigna el adapter del primer campo de busqueda
                    adapter1 = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_spinner_item,
                            gestionLibros.generosDisponibles());
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    generoBuscado.setAdapter(adapter1);
                    if (gestionLibros.getLibros().size() == 0) //si se eliminan todos los registros de la coleccion
                    {
                        //se limpian valores residuales en los spinner tras la eliminacion del ultimo registro
                        editorialBuscada.setAdapter(null);
                        autorBuscado.setAdapter(null);
                        tituloBuscado.setAdapter(null);
                        //se desactivan el boton lupa y lista
                        lupa.setEnabled(false);
                        lupa.setAlpha((float) 0.5);
                        lista.setEnabled(false);
                        lista.setAlpha((float) 0.5);
                    }


                }
                break;
            case REQUEST_ACTIVI4:

                GestionLibros gestion = (GestionLibros) data.getSerializableExtra("gestion");
                gestionLibros = gestion;

                //se inicializa y asigna el adapter del primer campo de busqueda
                adapter1 = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_spinner_item,
                        gestionLibros.generosDisponibles());
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                generoBuscado.setAdapter(adapter1);
                if (gestionLibros.getLibros().size() == 0) //si se eliminan todos los registros de la coleccion
                {
                    //se limpian valores residuales en los spinner tras la eliminacion del ultimo registro
                    editorialBuscada.setAdapter(null);
                    autorBuscado.setAdapter(null);
                    tituloBuscado.setAdapter(null);
                    //se desactivan el boton lupa y lista
                    lupa.setEnabled(false);
                    lupa.setAlpha((float) 0.5);
                    lista.setEnabled(false);
                    lista.setAlpha((float) 0.5);
                }


                break;
        }
    }


}
