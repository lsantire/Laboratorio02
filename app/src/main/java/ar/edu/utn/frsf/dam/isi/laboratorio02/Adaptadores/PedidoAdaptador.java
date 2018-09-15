package ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.Holders.PedidoHolder;
import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidoAdaptador extends ArrayAdapter<Pedido> {

    private Context ctx;
    private List<Pedido> datos;

    public PedidoAdaptador(Context context, List<Pedido> objects){
        super(context,0, objects);
        this.ctx = context;
        this.datos = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = convertView;
        if (fila == null) {
            fila = inflater.inflate(R.layout.fila_historial, parent, false);
        }
        PedidoHolder holder = (PedidoHolder) fila.getTag();
        if (holder == null) {
            holder = new PedidoHolder(fila);
            fila.setTag(holder);

            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indice = (int) view.getTag();
                    Pedido pedidoSeleccionado = datos.get(indice);
                    if( pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO)||
                            pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO)||
                            pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
                        pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
                        PedidoAdaptador.this.notifyDataSetChanged();
                        return;
                    }
                }
            };

        }
        Pedido pedido = (Pedido) super.getItem(position);
        holder.contacto.setText("Contacto: " + pedido.getMailContacto());
        holder.fechaDeEntrega.setText("Fecha de Entrega: " + pedido.getFecha());
        holder.itemsPagar.setText("Item: ");
        switch (pedido.getEstado()){
            case LISTO:
                holder.estado.setTextColor(Color.DKGRAY);
                break;
            case ENTREGADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                holder.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                holder.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                holder.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
        }
        holder.estado.setText("Estado: "+pedido.getEstado());

        return fila;
    }



}
