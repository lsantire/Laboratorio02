package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class VerHistorialActivity extends AppCompatActivity {

    private ListView lstHistorialPedidos;
    private ArrayAdapter<Pedido> adaptadorPedidos;
    private PedidoRepository pedidos;
    private Button btnHistorialNuevo;
    private Button btnHistorialMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_historial);

        lstHistorialPedidos = (ListView) findViewById(R.id.lstHistorialPedidos);
        btnHistorialNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnHistorialMenu = (Button) findViewById(R.id.btnHistorialMenu);
        pedidos = new PedidoRepository();

        adaptadorPedidos = new ArrayAdapter<Pedido>(VerHistorialActivity.this,android.R.layout.simple_list_item_1,pedidos.getLista());
        lstHistorialPedidos.setAdapter(adaptadorPedidos);

        btnHistorialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerHistorialActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnHistorialNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerHistorialActivity.this, NuevoPedidoActivity.class);
                startActivity(i);
            }
        });


        lstHistorialPedidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(VerHistorialActivity.this, NuevoPedidoActivity.class);
                i.putExtra("ID_PEDIDO",((Pedido)parent.getItemAtPosition(position)).getId());
                startActivity(i);
                return false;
            }
        });




    }
}
