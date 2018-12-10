package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

@Database(entities = { Categoria.class , Producto.class, Pedido.class, PedidoDetalle.class}, version = 3, exportSchema = false)
public abstract class DaoAbs extends RoomDatabase{

    public abstract CatDao getCat();

    public abstract ProDao getPro();

    public abstract PedDao getPed();

    public abstract DetalleDao getDetalle();

}
