package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

@Dao
public interface ProDao {

    @Query("SELECT * FROM producto")
    List<Producto> getAll();

    @Query("SELECT * FROM producto WHERE id IN (:userIds)")
    List<Producto> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM producto p WHERE p.cat_id = (:idCategoria)")
    List<Producto> getByIdCat(int idCategoria);

/*
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    */

    @Update
    void update(Producto... prod);

    @Insert
    void insert(Producto... prod);

    @Delete
    void delete(Producto... prod);

}
