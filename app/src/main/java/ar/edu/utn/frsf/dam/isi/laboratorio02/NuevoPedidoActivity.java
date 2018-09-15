package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class NuevoPedidoActivity extends AppCompatActivity {

    class RequestCode {
        static final int AGREGAR_PRODUCTO = 100;
    }

    Bundle extras;
    Pedido pedido;
    PedidoRepository repositorioPedidos;
    ProductoRepository repositorioProductos;
    String auxiliarDireccion;
    PedidoDetalle detallePedidoSeleccionado;

    ArrayAdapter<PedidoDetalle> adaptadorDetallePedido;

    RadioGroup optPedidoModoEntrega;
    RadioButton optPedidoRetira, optPedidoEnviar;
    EditText editDireccion,editPedidoCorreo;
    ListView listaProductosPedido;
    Button btnQuitarProductoPedido, btnAgregarProductoPedido,btnFinalizarPedido;
    TextView tvCostoTotalPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

        pedido = new Pedido();
        repositorioPedidos = new PedidoRepository();
        repositorioProductos = new ProductoRepository();
        auxiliarDireccion = new String();
        adaptadorDetallePedido = new ArrayAdapter<>(NuevoPedidoActivity.this, android.R.layout.simple_list_item_single_choice, pedido.getDetalle());
        extras = getIntent().getExtras();

        optPedidoModoEntrega = (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        optPedidoEnviar = (RadioButton) findViewById(R.id.optPedidoEnviar);
        optPedidoRetira = (RadioButton) findViewById(R.id.optPedidoRetira);
        editDireccion = (EditText) findViewById(R.id.editDireccion);
        listaProductosPedido = (ListView) findViewById(R.id.listProductosPedido);
        btnAgregarProductoPedido = (Button) findViewById(R.id.btnAgregarProductoPedido);
        btnQuitarProductoPedido = (Button) findViewById(R.id.btnQuitarProductoPedido);
        tvCostoTotalPedido = (TextView) findViewById(R.id.tvCostoTotalPedido);
        btnFinalizarPedido = (Button) findViewById(R.id.btnFinalizarPedido);
        editPedidoCorreo = (EditText) findViewById(R.id.editPedidoCorreo);

        tvCostoTotalPedido.setText(R.string.costoTotal+String.format("%.2f",pedido.total()));
        listaProductosPedido.setAdapter(adaptadorDetallePedido);

        if(getIntent().hasExtra("cantidad") && getIntent().hasExtra("idProducto")) onActivityResult(RequestCode.AGREGAR_PRODUCTO,Activity.RESULT_OK,getIntent());

        optPedidoModoEntrega.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == optPedidoEnviar.getId()) {
                    editDireccion.setEnabled(true);
                    editDireccion.setText(auxiliarDireccion);
                } else {
                    editDireccion.setText("");
                    editDireccion.setEnabled(false);
                }
            }
        });

        /*listaProductosPedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnQuitarProductoPedido.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnQuitarProductoPedido.setEnabled(false);
            }
        });*/

        listaProductosPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnQuitarProductoPedido.setEnabled(true);
                detallePedidoSeleccionado = (PedidoDetalle) parent.getItemAtPosition(position);
            }
        });

        btnQuitarProductoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedido.quitarDetalle(detallePedidoSeleccionado);
                adaptadorDetallePedido.notifyDataSetChanged();
                tvCostoTotalPedido.setText(R.string.costoTotal+String.format("$%.2f",pedido.total()));
            }
        });

        btnAgregarProductoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedidoActivity.this, VerProductosActivity.class);
                i.putExtra("fromNuevoPedido",0);
                startActivityForResult(i, RequestCode.AGREGAR_PRODUCTO);
            }
        });

        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readyToFinish())
                {
                    pedido.setDireccionEnvio(editDireccion.getText().toString());
                    pedido.setEstado(Pedido.Estado.REALIZADO);
                    pedido.setMailContacto(editPedidoCorreo.getText().toString());
                    pedido.setRetirar(optPedidoRetira.isChecked());
                    //pedido.setFecha();//HACER DESPUES
                    repositorioPedidos.guardarPedido(pedido);
                }
            }
        });

    }

    private boolean readyToFinish()
    {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int cantidad,idProductoAgregado;
        switch (requestCode) {
            case (RequestCode.AGREGAR_PRODUCTO): {
                if (resultCode == Activity.RESULT_OK) {
                    cantidad = data.getIntExtra("cantidad",-1);
                    idProductoAgregado = data.getIntExtra("idProducto",-1);
                    pedido.agregarDetalle(new PedidoDetalle(cantidad, repositorioProductos.buscarPorId(idProductoAgregado)));
                    adaptadorDetallePedido.notifyDataSetChanged();
                    tvCostoTotalPedido.setText(R.string.costoTotal+String.format("%.2f",pedido.total()));
                }
                break;
            }
        }
    }
}
