package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RoomMyProject;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class RestoMessagingService extends FirebaseMessagingService {
    public RestoMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        System.out.println("data id "+remoteMessage.getData().get("ID_PEDIDO"));
        Integer id = Integer.valueOf(remoteMessage.getData().get("ID_PEDIDO"));
        PedidoRepository pedidoRepository = new PedidoRepository();
        //Pedido pedido = pedidoRepository.buscarPorId(id);

        Pedido pedido = RoomMyProject.loadByIdPedido(id);

        
        if (!pedido.getEstado().equals(Pedido.Estado.LISTO)) {
            pedido.setEstado(Pedido.Estado.LISTO);
            Intent intenListo = new Intent(RestoMessagingService.this,EstadoPedidoReceiver.class);
            intenListo.putExtra("idPedido",pedido.getId());
            intenListo.setAction(EstadoPedidoReceiver.ESTADO_LISTO);
            sendBroadcast(intenListo);
        }
    }

}
