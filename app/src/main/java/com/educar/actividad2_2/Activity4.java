package com.educar.actividad2_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity4 extends AppCompatActivity {

    private EditText title;
    private ListView lista;
    private GestionLibros gestionLibros;
    private CustomAdapter adapter;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        title =(EditText)findViewById(R.id.et_tituloBusqueda);
        lista =(ListView)findViewById(R.id.lv_lista);
        gestionLibros =(GestionLibros)getIntent().getSerializableExtra("gestion");

        adapter = new CustomAdapter(this,R.layout.custom_layout,gestionLibros.getLibros());
        lista.setAdapter(adapter);
        back=(Button)findViewById(R.id.bt_volver);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //se obtiene el libro seleccionado del listview y se pasa este objeto a la Activity3
               Libro libro = (Libro) parent.getItemAtPosition(position);
               Intent intent = new Intent(Activity4.this, Activity3.class);
               intent.putExtra("libro", libro);

               startActivityForResult(intent, Activity2.REQUEST_ACTIVI3);

           }
       });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("gestion",gestionLibros);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Activity2.REQUEST_ACTIVI3:
                if (resultCode == RESULT_OK) { //se ha producido una modificacion en algun registro
                    Libro libroModificado = (Libro) data.getSerializableExtra("libroModi");
                    //se cambia el libro sin modificar por el libro modificado
                    gestionLibros.modificarLibro(libroModificado);
                }
                else if(resultCode == Activity2.RESULT_DELETE) //se ha eliminado un registro
                {
                    Libro libroBorrado =(Libro)data.getSerializableExtra("libroBorrado");
                    //se elimina ese libro de la coleccion
                    gestionLibros.eliminarRegistro(libroBorrado);
                    //se recarga el adaptador sin los datos eliminados y se añada a la lista
                    adapter = new CustomAdapter(this,R.layout.custom_layout,gestionLibros.getLibros());
                    lista.setAdapter(adapter);

                }
                break;

        }

    }

}
