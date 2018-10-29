package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;


import ar.edu.utn.frsf.dam.isi.laboratorio02.Mapeo.CategoriaDao;

@Dao
public interface CatDao {

    /*@Query("SELECT * FROM user")
    List<CategoriaDao> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<CategoriaDao> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    */

    @Update
    void update(CategoriaDao cat);

    @Insert
    void insert(CategoriaDao cat);

    @Delete
    void delete(CategoriaDao cat);

}
