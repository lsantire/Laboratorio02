package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class RoomMyProject {

    private static RoomMyProject _REPO = null;
    private static CatDao catDao;
    private static ProDao proDao;
    private static PedDao pedDao;
    private static DetalleDao DetalleDao;

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

    public static Producto getProdById(int productoIds){return proDao.getProdById(productoIds);}

    public static void insertProducto(Producto producto) {
        proDao.insert(producto);
    }

    public static void deleteProducto(Producto producto) {
        proDao.delete(producto);
    }

    public static void updateProducto(Producto producto) {
        proDao.update(producto);
    }


    public static List<Producto> getByIdCat(int idCategoria){
        return proDao.getByIdCat(idCategoria);
    }

    public static void insertPedido(Pedido unPedido) {
        DetalleDao.insertAll(unPedido.getDetalle());
        pedDao.insertAll(unPedido);
    }

    public static List<Pedido> getAllPedido() {
        List<Pedido> pedidos = pedDao.getAll();
        for (Pedido p : pedidos) {
            p.setDetalle(DetalleDao.loadAllByPedidoId(p.getId()));
        }
        return pedidos;
    }

    public static Pedido loadByIdPedido(Integer pedidoId){
        int[] pid = {pedidoId};

        Pedido p =pedDao.loadAllByIds(pid).get(0);
        p.setDetalle(DetalleDao.loadAllByPedidoId(p.getId()));
        return p;
    }


    private RoomMyProject() {
    }
}
