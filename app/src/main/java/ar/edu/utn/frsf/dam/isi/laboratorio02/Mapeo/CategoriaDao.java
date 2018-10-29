package ar.edu.utn.frsf.dam.isi.laboratorio02.Mapeo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CategoriaDao {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "Nombre")
    private String nombre;

}
