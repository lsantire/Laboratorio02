package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.ConexionJson.CategoriaRest;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class VerProductosActivity extends AppCompatActivity {

    private List<Producto> productos;
    private List<Categoria> categorias;
    private ArrayAdapter<Producto> adaptadorProductos;
    private ArrayAdapter<Categoria> adaptadorCategorias;
    private ProductoRepository repositorioProductos;
    private int idProductoSeleccionado;

    View selectedView;

    private Spinner spinnerCategorias;
    private ListView listProductos;
    private Button btnAgregarProducto;
    private EditText editCantidadProducto;

    private ArrayAdapter<Categoria> categoriasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_productos);

        repositorioProductos = new ProductoRepository();


        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        listProductos = (ListView) findViewById(R.id.listProductos);
        btnAgregarProducto = (Button) findViewById(R.id.btnAgregarProducto);
        editCantidadProducto = (EditText) findViewById(R.id.editCantidadProducto);

       // adaptadorCategorias = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, repositorioProductos.getCategorias());
       // spinnerCategorias.setAdapter(adaptadorCategorias);

        Runnable r = new Runnable() {
            @Override
            public void run() {

                CategoriaRest catRest = new CategoriaRest();
                try {
                    categorias = catRest.listarTodas();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                categoriasAdapter = new ArrayAdapter<Categoria>(VerProductosActivity.this, android.R.layout.simple_spinner_dropdown_item, categorias);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        spinnerCategorias.setAdapter(categoriasAdapter);
                        spinnerCategorias.setSelection(0);
                        productos = new ArrayList<>();

                        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int
                                    position, long id) {

                                adaptadorProductos.clear();
                                productos = repositorioProductos.buscarPorCategoria((Categoria) parent.getItemAtPosition(position));
                                adaptadorProductos.addAll(repositorioProductos.buscarPorCategoria(
                                        (Categoria) parent.getItemAtPosition(position))
                                );

                                adaptadorProductos.notifyDataSetChanged();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        adaptadorProductos = new ArrayAdapter<Producto>(VerProductosActivity.this, android.R.layout.simple_list_item_single_choice, productos);
                        listProductos = (ListView) findViewById(R.id.listProductos);
                        listProductos.setAdapter(adaptadorProductos);
                    }
                });
            }
        };
        Thread hiloCargarCombo = new Thread(r);
        hiloCargarCombo.start();

/*
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnAgregarProducto.setEnabled(false);
                editCantidadProducto.setText("");
                editCantidadProducto.setEnabled(false);
                idProductoSeleccionado = -1;
                productos = repositorioProductos.buscarPorCategoria((Categoria) parent.getItemAtPosition(position));
                adaptadorProductos = new ArrayAdapter<Producto>(VerProductosActivity.this, android.R.layout.simple_list_item_single_choice, productos);
                listProductos.setAdapter(adaptadorProductos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnAgregarProducto.setEnabled(false);
                editCantidadProducto.setText("");
                editCantidadProducto.setEnabled(false);
            }
        });
*/
        listProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idProductoSeleccionado = ((Producto) parent.getItemAtPosition(position)).getId();
                editCantidadProducto.setText("1");
                editCantidadProducto.setEnabled(true);
                btnAgregarProducto.setEnabled(true);
            }
        });

      /*  listProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editCantidadProducto.setText("1");
                editCantidadProducto.setEnabled(true);
                btnAgregarProducto.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnAgregarProducto.setEnabled(false);
                editCantidadProducto.setText("");
                editCantidadProducto.setEnabled(false);
            }
        });*/

        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("fromNuevoPedido")) {
                    Intent i = new Intent();
                    i.putExtra("cantidad", Integer.valueOf(editCantidadProducto.getText().toString()));
                    i.putExtra("idProducto", idProductoSeleccionado);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                } else {
                    Intent i = new Intent(VerProductosActivity.this, NuevoPedidoActivity.class);
                    i.putExtra("cantidad", Integer.valueOf(editCantidadProducto.getText().toString()));
                    i.putExtra("idProducto", idProductoSeleccionado);
                    startActivity(i);
                    finish();
                }

            }
        });

        editCantidadProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) btnAgregarProducto.setEnabled(false);
                else btnAgregarProducto.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
