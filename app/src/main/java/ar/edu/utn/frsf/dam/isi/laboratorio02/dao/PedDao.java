package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
@Dao
public interface PedDao {
    @Query("SELECT * FROM pedido")
    List<Pedido> getAll();

    @Query("SELECT * FROM pedido WHERE id IN (:pedidoIds)")
    List<Pedido> loadAllByIds(int[] pedidoIds);

    @Insert
    void insertAll(Pedido... pedido);

    @Delete
    void delete(Pedido pedido);

    @Update
    void update(Pedido pedido);
}
