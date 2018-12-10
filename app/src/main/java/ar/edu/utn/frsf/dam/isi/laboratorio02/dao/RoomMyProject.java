package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoConDetalles;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class RoomMyProject {

    private static RoomMyProject _REPO = null;
    private static CatDao catDao;
    private static ProDao proDao;
    private static PedDao pedDao;
    private static DetalleDao detalleDao;

    private RoomMyProject(Context ctx) {
        DaoAbs db = Room.databaseBuilder(ctx, DaoAbs.class, "14dam-pry-2018").fallbackToDestructiveMigration().build();
        catDao = db.getCat();
        proDao = db.getPro();
        pedDao = db.getPed();
        detalleDao = db.getDetalle();
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
        System.out.println(unPedido.getDetalle().toString());
        System.out.println(unPedido.toString());
        int id = (int)pedDao.insert(unPedido);
        unPedido.setId(id);
        for(PedidoDetalle pd : unPedido.getDetalle()) pd.setPedido(unPedido);
        detalleDao.insertAll(unPedido.getDetalle());
    }

    public static List<Pedido> getAllPedido() {
        List<PedidoConDetalles> pedidosConDetalles = pedDao.getAll();
        System.out.println("ACA!!!!!!!!"+pedidosConDetalles.size());
        List<Pedido> pedidos = new ArrayList();
        for(PedidoConDetalles p : pedidosConDetalles){
            System.out.println("ACA!!!!!!!!"+p.getDetalle().size()+" "+p.getPedido());
            pedidos.add(p.getPedido());
            for(PedidoDetalle pd : p.getDetalle())
            {
                pedidos.get(pedidos.size()-1).agregarDetalle(pd);
            }
        }
        return pedidos;
    }

    public static void updatePedido(Pedido unPedido)
    {
        pedDao.update(unPedido);
    }

    public static Pedido loadByIdPedido(Integer pedidoId){

        List<PedidoConDetalles> pedidosConDetalles = pedDao.loadAllByIds(pedidoId);
        for(PedidoConDetalles pcd : pedidosConDetalles){
            Pedido p = pcd.getPedido();
            for(PedidoDetalle pd : pcd.getDetalle())
            {
                p.agregarDetalle(pd);
            }
            return p;
        }
        return null;
    }


    private RoomMyProject() {
    }
}
