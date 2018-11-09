package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;


@Dao
public interface CatDao {

    @Query("SELECT * FROM Categoria")
    List<Categoria> getAll();

    @Query("SELECT * FROM categoria WHERE id IN (:catId)")
    List<Categoria> loadAllByIds(int[] catId);

    /*
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    */

    @Update
    void update(Categoria... cat);

    @Insert
    void insert(Categoria... cat);

    @Delete
    void delete(Categoria... cat);

}
