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
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RoomMyProject;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static final String ESTADO_ACEPTADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_ACEPTADO";
    public static final String ESTADO_CANCELADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static final String ESTADO_EN_PREPARACION = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static final String ESTADO_LISTO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_LISTO";

    PedidoRepository repositorioPedidos = new PedidoRepository();

    private Context context;
    private Intent intent;


    @Override
    public void onReceive(Context contextp, Intent intentp) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
            System.out.println("recibi broadcast");
            context=contextp;
            intent=intentp;
        if(intent.getAction()!=null) switch (intent.getAction()){
            case ESTADO_ACEPTADO:{
                if(intent.hasExtra("idPedido")){
                   // Pedido p = repositorioPedidos.buscarPorId(intent.getExtras().getInt("idPedido")); VIEJO ESTATICO

                    Runnable rGetPedido = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Pedido p = RoomMyProject.loadByIdPedido(intent.getExtras().getInt("idPedido"));

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
                                notifManager.notify((int)(Math.random()*10000),notifAceptado);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };

                    Thread hiloGetPedido = new Thread(rGetPedido);
                    hiloGetPedido.start();


                }
                break;
            }
            case ESTADO_EN_PREPARACION:{
                if(intent.hasExtra("idPedido")){
                   // Pedido p = repositorioPedidos.buscarPorId(intent.getExtras().getInt("idPedido"));

                    Runnable rGetPedido = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Pedido p = RoomMyProject.loadByIdPedido(intent.getExtras().getInt("idPedido"));

                                Intent destino = new Intent(context, VerHistorialActivity.class);
                                destino.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,destino,PendingIntent.FLAG_UPDATE_CURRENT);
                                Notification notifEnPreparacion = new NotificationCompat.Builder(context, "CANAL01")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentTitle("Tu pedido esta en preparacion")
                                        .setContentText("El costo será de "+String.format("$%.2f", p.total())+
                                                "\nPrevisto el envio para "+p.getFecha())
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .build();

                                NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
                                notifManager.notify((int)(Math.random()*10000),notifEnPreparacion);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };

                    Thread hiloGetPedido = new Thread(rGetPedido);
                    hiloGetPedido.start();


                }
                break;

            }
            case ESTADO_LISTO: {
                if (intent.hasExtra("idPedido")) {
                    //Pedido p = repositorioPedidos.buscarPorId(intent.getExtras().getInt("idPedido"));

                    Runnable rGetPedido = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Pedido p = RoomMyProject.loadByIdPedido(intent.getExtras().getInt("idPedido"));

                                Intent destino = new Intent(context, VerHistorialActivity.class);
                                destino.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);
                                Notification notifEnPreparacion = new NotificationCompat.Builder(context, "CANAL01")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentTitle("Tu pedido esta listo")
                                        .setContentText("El costo será de " + String.format("$%.2f", p.total()) +
                                                "\nPrevisto el envio para " + p.getFecha())
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .build();

                                NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
                                notifManager.notify((int) (Math.random() * 10000), notifEnPreparacion);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };

                    Thread hiloGetPedido = new Thread(rGetPedido);
                    hiloGetPedido.start();


                }
                break;
            }
        }
    }
}
