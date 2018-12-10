package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.ConexionJson.RestClient;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RoomMyProject;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity {
    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private ArrayAdapter<Categoria> comboAdapter;
    private Categoria catAux;
    private List<Categoria> categorias;
    private int idProd;
    private Producto prodAux;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);
        opcionNuevoBusqueda = (ToggleButton) findViewById(R.id.abmProductoAltaNuevo);
        idProductoBuscar = (EditText) findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = (EditText) findViewById(R.id.abmProductoNombre);
        descProducto = (EditText) findViewById(R.id.abmProductoDescripcion);
        precioProducto = (EditText) findViewById(R.id.abmProductoPrecio);
        comboCategorias = (Spinner) findViewById(R.id.abmProductoCategoria);
        btnMenu = (Button) findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = (Button) findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = (Button) findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar = (Button) findViewById(R.id.btnAbmProductoBorrar);
        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);

        idProductoBuscar.setEnabled(false);

        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnBuscar.setEnabled(isChecked);
                btnBorrar.setEnabled(false);
                btnGuardar.setEnabled(!isChecked);
                idProductoBuscar.setEnabled(isChecked);
            }
        });


        Runnable r = new Runnable() {
            @Override
            public void run() {

               /* CategoriaRest catRest = new CategoriaRest();
                List<Categoria> categorias = null;*/


                try {
                    RoomMyProject.getInstance(getApplicationContext()); //Crea la DB
                    categorias = RoomMyProject.getAll(); //Trea todas las categorias

                    // categorias = catRest.listarTodas();

                    comboAdapter = new ArrayAdapter<Categoria>(GestionProductoActivity.this, android.R.layout.simple_spinner_dropdown_item, categorias);
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comboCategorias.setAdapter(comboAdapter);
                        comboCategorias.setSelection(0);
                    }
                });
            }
        };

        Thread hiloCargarCombo = new Thread(r);
        hiloCargarCombo.start();

        comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catAux = (Categoria) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nombreProducto.getText().toString().isEmpty() && !descProducto.getText().toString().isEmpty() && !precioProducto.getText().toString().isEmpty()) {
                    //Producto p = new Producto(nombreProducto.getText().toString(), descProducto.getText().toString(), Double.parseDouble(String.valueOf(precioProducto.getText())), catAux);
                    /*ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                    Call<Producto> altaCall = clienteRest.crearProducto(p);
                    altaCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> resp) {
                            nombreProducto.setText("");
                            descProducto.setText("");
                            precioProducto.setText("");
                            comboCategorias.setSelection(0);
                            Toast.makeText(GestionProductoActivity.this,"El producto "+resp.body().getNombre() +" se agrego en la categoria "+resp.body().getCategoria().getNombre(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                        }
                    });*/

                    Runnable rCrearCategoria = new Runnable() {
                        @Override
                        public void run() {

                            Producto p = new Producto(nombreProducto.getText().toString(), descProducto.getText().toString(), Double.parseDouble(String.valueOf(precioProducto.getText())), catAux);

                            try {
                                // categoriaRest.crearCategoria(cat);
                                RoomMyProject.getInstance(getApplicationContext()); //Crea la DB
                                RoomMyProject.insertProducto(p);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GestionProductoActivity.this, "Producto creada!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    };
                    Thread hiloCrearProducto = new Thread(rCrearCategoria);
                    hiloCrearProducto.start();

                } else {
                    Toast.makeText(GestionProductoActivity.this, "Complete los campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!idProductoBuscar.getText().toString().isEmpty()) {
                   /* ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                    Call<Producto> altaCall = clienteRest.buscarProductoPorId(Integer.parseInt(idProductoBuscar.getText().toString()));
                    altaCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> resp) {

                            if (resp.body() != null) {
                                nombreProducto.setText(resp.body().getNombre());
                                descProducto.setText(resp.body().getDescripcion());
                                precioProducto.setText(resp.body().getPrecio().toString());
                                comboCategorias.setSelection(comboAdapter.getPosition(resp.body().getCategoria()));
                            }else{
                                nombreProducto.setText("");
                                descProducto.setText("");
                                precioProducto.setText("");
                                comboCategorias.setSelection(0);
                                Toast.makeText(GestionProductoActivity.this, "No encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {

                        }
                    });*/
                    idProd = Integer.parseInt(idProductoBuscar.getText().toString());

                    Runnable rCrearCategoria = new Runnable() {
                        @Override
                        public void run() {
                            RoomMyProject.getInstance(getApplicationContext()); //Crea la DB
                                // categoriaRest.crearCategoria(cat);
                                prodAux = (Producto) RoomMyProject.getProdById(idProd);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (prodAux != null) {
                                            nombreProducto.setText(prodAux.getNombre());
                                            descProducto.setText(prodAux.getDescripcion());
                                            precioProducto.setText(prodAux.getPrecio().toString());
                                            comboCategorias.setSelection(comboAdapter.getPosition(prodAux.getCategoria()));
                                            btnBorrar.setEnabled(true);
                                        } else {
                                            btnBorrar.setEnabled(false);
                                            nombreProducto.setText("");
                                            descProducto.setText("");
                                            precioProducto.setText("");
                                            comboCategorias.setSelection(0);
                                            Toast.makeText(GestionProductoActivity.this, "No encontrado", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                        }

                    };
                    Thread hiloBuscarProd = new Thread(rCrearCategoria);
                    hiloBuscarProd.start();

                } else {
                    Toast.makeText(GestionProductoActivity.this, "Complete el id", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!idProductoBuscar.getText().toString().isEmpty()) {
                   /*
                    ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                    Call<Producto> altaCall = clienteRest.borrar(Integer.parseInt(idProductoBuscar.getText().toString()));
                    altaCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> resp) {
                            nombreProducto.setText("");
                            descProducto.setText("");
                            precioProducto.setText("");
                            comboCategorias.setSelection(0);
                            Toast.makeText(GestionProductoActivity.this, "Borrado!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                        }
                    });
*/

                    Runnable rCrearCategoria = new Runnable() {
                        @Override
                        public void run() {

                            try {
                                // categoriaRest.crearCategoria(cat);
                                RoomMyProject.getInstance(getApplicationContext()); //Crea la DB
                                RoomMyProject.deleteProducto(prodAux);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        nombreProducto.setText("");
                                        descProducto.setText("");
                                        precioProducto.setText("");
                                        comboCategorias.setSelection(0);
                                        btnBorrar.setEnabled(false);
                                        Toast.makeText(GestionProductoActivity.this, "Borrado!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    };
                    Thread hiloBorrarProducto = new Thread(rCrearCategoria);
                    hiloBorrarProducto.start();

                } else {
                    Toast.makeText(GestionProductoActivity.this, "Complete el id", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}



