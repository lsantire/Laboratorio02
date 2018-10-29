package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RoomMyProject {

    private static RoomMyProject _REPO = null;
    private CatDao catDao;

    private RoomMyProject(Context ctx) {
        DaoAbs db = Room.databaseBuilder(ctx, DaoAbs.class, "dam-pry-2018").fallbackToDestructiveMigration().build();
        catDao = db.getDao();
    }

    public static RoomMyProject getInstance(Context ctx) {
        if (_REPO == null) _REPO = new RoomMyProject(ctx);
        return _REPO;


    }

    private RoomMyProject() {
    }
}
