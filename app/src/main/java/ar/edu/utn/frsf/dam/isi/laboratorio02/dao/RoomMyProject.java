package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class RoomMyProject {

    private static RoomMyProject _REPO = null;
    private static CatDao catDao;
    private static ProDao proDao;

    private RoomMyProject(Context ctx) {
        DaoAbs db = Room.databaseBuilder(ctx, DaoAbs.class, "dam-pry-2018").fallbackToDestructiveMigration().build();
        catDao = db.getCat();
        proDao = db.getPro();
    }

    public static RoomMyProject getInstance(Context ctx) {
        if (_REPO == null) _REPO = new RoomMyProject(ctx);
        return _REPO;


    }

    public static List<Categoria> getAll() {
        return catDao.getAll();
    }

    public static List<Categoria> loadAllByIdsCat(int[] categoriaIds) {
        return catDao.loadAllByIds(categoriaIds);
    }

    public static void insert(Categoria... categorias) {
        catDao.insert(categorias);
    }

    public static void delete(Categoria categoria) {
        catDao.delete(categoria);
    }

    public static void update(Categoria categoria) {
        catDao.update(categoria);
    }

    public static List<Producto> getAllProducto(){return proDao.getAll();}

    public static List<Producto> loadAllByIdsProducto(int[] productoIds){return proDao.loadAllByIds(productoIds);}

    public static void insertProducto(Producto producto) {
        proDao.insert(producto);
    }

    public static void deleteProducto(Producto producto) {
        proDao.delete(producto);
    }

    public static void updateProducto(Producto producto) {
        proDao.update(producto);
    }

    public static List<Producto> loadAllByIdsProd(int[] prodId) {
        return proDao.loadAllByIds(prodId);
    }


    private RoomMyProject() {
    }
}
