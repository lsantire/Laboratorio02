package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RoomMyProject;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PrepararPedidoService extends IntentService {
    public PrepararPedidoService() {
        super("PrepararPedidoService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        try{
            Thread.currentThread().sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RoomMyProject.getInstance(getApplicationContext()); //Crea la DB

        PedidoRepository repositorioPedidos = new PedidoRepository();
        for(Pedido p: RoomMyProject.getAllPedido()  /*repositorioPedidos.getLista() VIEJO */){
            if(p.getEstado().equals(Pedido.Estado.ACEPTADO)){
                p.setEstado(Pedido.Estado.EN_PREPARACION);
                Intent intentEnPreparacion = new Intent(PrepararPedidoService.this,EstadoPedidoReceiver.class);
                intentEnPreparacion.putExtra("idPedido",p.getId());
                intentEnPreparacion.setAction(EstadoPedidoReceiver.ESTADO_EN_PREPARACION);
                sendBroadcast(intentEnPreparacion);
            }
        }
    }
}
