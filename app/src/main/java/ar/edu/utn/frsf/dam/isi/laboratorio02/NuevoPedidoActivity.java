package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    SimpleDateFormat sdf;
    ArrayAdapter<PedidoDetalle> adaptadorDetallePedido;

    RadioGroup optPedidoModoEntrega;
    RadioButton optPedidoRetira, optPedidoEnviar;
    EditText editDireccion, editPedidoCorreo, editHoraEntrega;
    ListView listaProductosPedido;
    Button btnQuitarProductoPedido, btnAgregarProductoPedido, btnFinalizarPedido, btnVolverPedido;
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

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("HH:mm");

        btnVolverPedido = (Button) findViewById(R.id.btnVolverPedido);
        editHoraEntrega = (EditText) findViewById(R.id.editHoraEntrega);
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



        tvCostoTotalPedido.setText(getResources().getString(R.string.costoTotal) + String.format("$%.2f", pedido.total()));
        listaProductosPedido.setAdapter(adaptadorDetallePedido);

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        optPedidoRetira.setChecked(preferencias.getBoolean("preferenciaRetirar",false));
        optPedidoEnviar.setChecked(!preferencias.getBoolean("preferenciaRetirar",true));
        editPedidoCorreo.setText(preferencias.getString("preferenciaCorreoElectronico",""));



        System.out.println("nuevoPedidoActivity");

        if (getIntent().hasExtra("idPedidoSeleccionado")) {
            int idPedido;
            System.out.println("tiene idPedidoSeleccionado");
            idPedido = getIntent().getExtras().getInt("idPedidoSeleccionado");

            if (idPedido >= 0) {
                pedido = repositorioPedidos.buscarPorId(idPedido);
                editPedidoCorreo.setText(pedido.getMailContacto());
                editDireccion.setText(pedido.getDireccionEnvio());
                editHoraEntrega.setText(sdf.format(pedido.getFecha()));
                optPedidoEnviar.setChecked(!pedido.getRetirar());
                optPedidoRetira.setChecked(pedido.getRetirar());
                tvCostoTotalPedido.setText(getResources().getString(R.string.costoTotal) + String.format("$%.2f", pedido.total()));


                adaptadorDetallePedido = new ArrayAdapter<>(NuevoPedidoActivity.this, android.R.layout.simple_list_item_1, pedido.getDetalle());
                listaProductosPedido.setAdapter(adaptadorDetallePedido);
                editPedidoCorreo.setEnabled(false);
                editDireccion.setEnabled(false);
                editHoraEntrega.setEnabled(false);
                optPedidoEnviar.setEnabled(false);
                optPedidoRetira.setEnabled(false);
                btnAgregarProductoPedido.setEnabled(false);
                btnQuitarProductoPedido.setEnabled(false);
                btnFinalizarPedido.setEnabled(false);
                listaProductosPedido.setChoiceMode(ListView.CHOICE_MODE_NONE);

            }


        }


        if (getIntent().hasExtra("cantidad") && getIntent().hasExtra("idProducto"))
            onActivityResult(RequestCode.AGREGAR_PRODUCTO, Activity.RESULT_OK, getIntent());

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
                if (!getIntent().hasExtra("idPedidoSeleccionado")) {
                    btnQuitarProductoPedido.setEnabled(true);
                    detallePedidoSeleccionado = (PedidoDetalle) parent.getItemAtPosition(position);
                }
            }
        });

        btnQuitarProductoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedido.quitarDetalle(detallePedidoSeleccionado);
                adaptadorDetallePedido.notifyDataSetChanged();
                tvCostoTotalPedido.setText(getResources().getString(R.string.costoTotal) + String.format("$%.2f", pedido.total()));
                btnQuitarProductoPedido.setEnabled(false);

            }
        });

        btnAgregarProductoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedidoActivity.this, VerProductosActivity.class);
                i.putExtra("fromNuevoPedido", 0);
                startActivityForResult(i, RequestCode.AGREGAR_PRODUCTO);
            }
        });

        btnVolverPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readyToFinish()) {
                    String[] horaIngresada = editHoraEntrega.getText().toString().split(":");
                    GregorianCalendar hora = new GregorianCalendar();
                    int valorHora = Integer.valueOf(horaIngresada[0]);
                    int valorMinuto = Integer.valueOf(horaIngresada[1]);
                    if (valorHora < 0 || valorHora > 23) {
                        Toast.makeText(NuevoPedidoActivity.this,
                                "La hora ingresada (" + valorHora + " es incorrecta )",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (valorMinuto < 0 || valorMinuto > 59) {
                        Toast.makeText(NuevoPedidoActivity.this,
                                "Los minutos (" + valorMinuto + " son incorrectos )",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    hora.set(Calendar.HOUR_OF_DAY, valorHora);
                    hora.set(Calendar.MINUTE, valorMinuto);
                    hora.set(Calendar.SECOND, Integer.valueOf(0));

                    pedido.setFecha(hora.getTime());
                    pedido.setDireccionEnvio(editDireccion.getText().toString());
                    pedido.setEstado(Pedido.Estado.REALIZADO);
                    pedido.setMailContacto(editPedidoCorreo.getText().toString());
                    pedido.setRetirar(optPedidoRetira.isChecked());
                    repositorioPedidos.guardarPedido(pedido);

                    Runnable rAceptarPedidos = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                System.out.println("antes del sleep");
                                Thread.currentThread().sleep(10000);
                                System.out.println("despues del sleep");

                            } catch (InterruptedException e) {
                                e.printStackTrace();

                            }

                            //buscar pedidos no aceptados y aceptarlos automaticamente
                            for(Pedido p:repositorioPedidos.getLista()){
                                if(p.getEstado().equals(Pedido.Estado.REALIZADO))
                                {
                                    p.setEstado(Pedido.Estado.ACEPTADO);
                                    Intent intentAceptado = new Intent(NuevoPedidoActivity.this,EstadoPedidoReceiver.class);
                                    intentAceptado.putExtra("idPedido",p.getId());
                                    intentAceptado.setAction(EstadoPedidoReceiver.ESTADO_ACEPTADO);
                                    sendBroadcast(intentAceptado);
                                }
                            }

                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NuevoPedidoActivity.this,"Informacion de pedidos actualizada!",Toast.LENGTH_LONG).show();
                                }
                            });*/
                        }
                    };

                    Thread hiloActualizacionEstadoPedidos = new Thread(rAceptarPedidos);
                    hiloActualizacionEstadoPedidos.start();

                    Intent i = new Intent(NuevoPedidoActivity.this, VerHistorialActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(NuevoPedidoActivity.this, "NO ANDA VIEJO", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean readyToFinish() {


        return !editPedidoCorreo.getText().toString().isEmpty() && !editHoraEntrega.toString().isEmpty() && listaProductosPedido.getChildCount() > 0 &&
                (optPedidoRetira.isChecked() || !editDireccion.getText().toString().isEmpty()) && formatoHoraCorrecto();
    }

    private boolean formatoHoraCorrecto() {
        int contadorDeDosPuntos = 0;
        for (int i = 0; i < editHoraEntrega.getText().toString().length(); i++) {
            if (editHoraEntrega.getText().toString().charAt(i) == ':') contadorDeDosPuntos++;
        }
        return contadorDeDosPuntos == 1 && editHoraEntrega.getText().toString().charAt(0) != ':'
                && editHoraEntrega.getText().toString().charAt(editHoraEntrega.getText().toString().length() - 1) != ':';
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int cantidad, idProductoAgregado;
        switch (requestCode) {
            case (RequestCode.AGREGAR_PRODUCTO): {
                if (resultCode == Activity.RESULT_OK) {
                    cantidad = data.getIntExtra("cantidad", -1);
                    idProductoAgregado = data.getIntExtra("idProducto", -1);
                    pedido.agregarDetalle(new PedidoDetalle(cantidad, repositorioProductos.buscarPorId(idProductoAgregado)));
                    adaptadorDetallePedido.notifyDataSetChanged();
                    tvCostoTotalPedido.setText(getResources().getString(R.string.costoTotal) + String.format("$%.2f", pedido.total()));
                }
                break;
            }
        }
    }
}
