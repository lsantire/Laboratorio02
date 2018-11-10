package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;


@Dao
public interface DetalleDao {
    @Query( "SELECT * FROM pedidodetalle")
    List<PedidoDetalle> getAll();

    @Query("SELECT * FROM pedidodetalle WHERE id IN (:pedidodetalleIds)")
    List<PedidoDetalle> loadAllByIds(int[] pedidodetalleIds);

    @Query("SELECT * FROM pedidodetalle WHERE ped_id IN (:pedidoId)")
    List<PedidoDetalle> loadAllByPedidoId(int pedidoId);

    @Insert
    void insertAll(List<PedidoDetalle> pedido);

    @Delete
    void delete(PedidoDetalle pedido);

    @Update
    void update(PedidoDetalle pedido);
}
