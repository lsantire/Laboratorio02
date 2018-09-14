package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class VerProductosActivity extends AppCompatActivity {

    private Spinner spinnerCategorias;
    private ListView listProductos;
    private ArrayAdapter<Producto> adaptadorProductos;
    private ArrayAdapter<Categoria> adaptadorCategorias;
    private ProductoRepository productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_productos);

        productos =  new ProductoRepository();

        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        listProductos = (ListView) findViewById(R.id.listProductos);

        adaptadorCategorias = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, productos.getCategorias());
        spinnerCategorias.setAdapter(adaptadorCategorias);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adaptadorProductos = new ArrayAdapter<Producto>(VerProductosActivity.this,android.R.layout.simple_list_item_1, productos.buscarPorCategoria((Categoria) parent.getItemAtPosition(position)));
                listProductos.setAdapter(adaptadorProductos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
