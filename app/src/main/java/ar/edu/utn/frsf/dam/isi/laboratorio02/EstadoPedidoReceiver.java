package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static final String ESTADO_ACEPTADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_ACEPTADO";
    public static final String ESTADO_CANCELADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static final String ESTADO_EN_PREPARACION = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static final String ESTADO_LISTO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_LISTO";

    PedidoRepository repositorioPedidos = new PedidoRepository();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction()!=null) switch (intent.getAction()){
            case ESTADO_ACEPTADO:{
                if(intent.hasExtra("idPedido")){
                    Pedido p = repositorioPedidos.buscarPorId(intent.getExtras().getInt("idPedido"));

                    Intent destino = new Intent(context, NuevoPedidoActivity.class);
                    destino.putExtra("idPedidoSeleccionado",p.getId());
                    destino.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(context,0,destino,PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notifAceptado = new NotificationCompat.Builder(context, "CANAL01")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Tu pedido fue aceptado")
                            .setContentText("El costo será de "+String.format("$%.2f", p.total())+
                                    "\nPrevisto el envio para "+p.getFecha())
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build();

                    NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
                    notifManager.notify(192548,notifAceptado);
                }
            }
        }
    }
}
