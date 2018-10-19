package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CategoriaActivity extends AppCompatActivity {

        private EditText txtNombreCategoria;
        private Button btnCrearCategoria,btnCategoriaVolver;

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
                    //completar
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
