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

    private RemoteMessage remoteMessage2;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("data id " + remoteMessage.getData().get("ID_PEDIDO"));
        remoteMessage2 = remoteMessage;

        //PedidoRepository pedidoRepository = new PedidoRepository();
        //Pedido pedido = pedidoRepository.buscarPorId(id);

        Runnable rGetPedido = new Runnable() {
            @Override
            public void run() {

                Integer id = Integer.valueOf(remoteMessage2.getData().get("ID_PEDIDO"));

                Pedido pedido = RoomMyProject.loadByIdPedido(id);


                if (!pedido.getEstado().equals(Pedido.Estado.LISTO)) {
                    pedido.setEstado(Pedido.Estado.LISTO);
                    Intent intentListo = new Intent(RestoMessagingService.this, EstadoPedidoReceiver.class);
                    intentListo.putExtra("idPedido", pedido.getId());
                    intentListo.setAction(EstadoPedidoReceiver.ESTADO_LISTO);
                    sendBroadcast(intentListo);
                }
            }
        };

        Thread hiloGetPedido = new Thread(rGetPedido);
        hiloGetPedido.start();
    }

}
