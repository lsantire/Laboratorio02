package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import ar.edu.utn.frsf.dam.isi.laboratorio02.ConexionJson.CategoriaRest;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private EditText txtNombreCategoria;
    private Button btnCrearCategoria, btnCategoriaVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        txtNombreCategoria = findViewById(R.id.txtNombreCategoria);
        btnCrearCategoria = findViewById(R.id.btnCrearCategoria);
        btnCategoriaVolver = findViewById(R.id.btnCategoriaVolver);

        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Runnable rCrearCategoria = new Runnable() {
                    @Override
                    public void run() {
                        Categoria cat = new Categoria();
                        cat.setNombre(txtNombreCategoria.getText().toString());
                        CategoriaRest categoriaRest = new CategoriaRest();
                        try {
                            categoriaRest.crearCategoria(cat);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtNombreCategoria.setText("");
                                    Toast.makeText(CategoriaActivity.this, "Categoria creada!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                };
                Thread hiloCrearCategoria = new Thread(rCrearCategoria);
                hiloCrearCategoria.start();

            }
        });

        btnCategoriaVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
