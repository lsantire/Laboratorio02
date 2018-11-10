package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.arch.persistence.room.RoomOpenHelper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores.PedidoAdaptador;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RoomMyProject;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class VerHistorialActivity extends AppCompatActivity {

    private ListView lstHistorialPedidos;
    private PedidoAdaptador adaptadorPedidos;
    private PedidoRepository pedidosRepository;
    private Button btnHistorialNuevo;
    private Button btnHistorialMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_historial);

        lstHistorialPedidos = (ListView) findViewById(R.id.lstHistorialPedidos);
        btnHistorialNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnHistorialMenu = (Button) findViewById(R.id.btnHistorialMenu);

        RoomMyProject.getInstance(getApplicationContext()); //Crea la DB

        pedidosRepository = new PedidoRepository();

        adaptadorPedidos = new PedidoAdaptador(VerHistorialActivity.this, RoomMyProject.getAllPedido() /*pedidosRepository.getLista() VIEJO */);
        lstHistorialPedidos.setAdapter(adaptadorPedidos);

        btnHistorialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnHistorialNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerHistorialActivity.this, NuevoPedidoActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
