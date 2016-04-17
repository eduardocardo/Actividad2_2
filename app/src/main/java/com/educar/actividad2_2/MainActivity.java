package com.educar.actividad2_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText login;
    private Button aceptar;
    //indica la contraseña para acceder a la aplicacion
    public static final String PASSWORD ="1234";
    //es el numero de intentos para aceptar el login
    public static  int NUM_INTENTOS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login =(EditText)findViewById(R.id.et_login);
        aceptar =(Button)findViewById(R.id.bt_login);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().length() > 0)
                {
                    if(login.getText().toString().equals(PASSWORD))
                    {
                        Intent intent = new Intent(MainActivity.this,Activity2.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        NUM_INTENTOS--;

                        if(NUM_INTENTOS != 0)
                        {
                            Toast.makeText(MainActivity.this,String.format(getResources().getString(R.string.ErrorPass),NUM_INTENTOS)
                                    ,Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            finish();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.mensajeLogin),Toast.LENGTH_SHORT).show();
                }
                //se limpian los datos
                login.setText("");
            }

        });

    }


}
