package ar.edu.utn.frsf.dam.isi.laboratorio02.Holders;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;


public class PedidoHolder {

    public TextView textView3;
    public TextView contacto;
    public ImageView imageView;
    public TextView fechaDeEntrega;
    public TextView itemsPagar;
    public TextView estado;
    public Button button;
    public Button button2;

    public PedidoHolder(View base) {
        this.textView3 = (TextView) base.findViewById(R.id.textView3);
        this.contacto = (TextView) base.findViewById(R.id.contacto);
        this.imageView = (ImageView) base.findViewById(R.id.imageView);
        this.fechaDeEntrega = (TextView) base.findViewById(R.id.fechaDeEntrega);
        this.itemsPagar = (TextView) base.findViewById(R.id.itemsPagar);
        this.estado = (TextView) base.findViewById(R.id.estado);
        this.button = (Button) base.findViewById(R.id.button);
        this.button2 = (Button) base.findViewById(R.id.button2);
    }

}
