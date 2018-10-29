package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ar.edu.utn.frsf.dam.isi.laboratorio02.Mapeo.CategoriaDao;

@Database(entities = { CategoriaDao.class }, version = 1, exportSchema = false)
public abstract class DaoAbs extends RoomDatabase{

    public abstract CatDao getDao();



}
