package cpc.demeter.comando.mantenimiento;

import java.util.Hashtable;
import java.util.Map;

import cpc.ares.interfaz.IAplicacion;
import cpc.ares.interfaz.IComando;
import cpc.ares.modelo.AccionFuncionalidad;
import cpc.demeter.AppDemeter;
import cpc.demeter.catalogo.mantenimiento.ContCotizacionesEquipo;
import cpc.zk.componente.interfaz.IZkAplicacion;

public class ComandoCotizacionEquipo implements IComando {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5684053295080033398L;
	private AccionFuncionalidad funcionalidades;
	private Map<String, Object> mapa = new Hashtable<String, Object>();
	private int id;
	private IZkAplicacion app;

	public void ejecutar() {
		try {
			ContCotizacionesEquipo servicio = new ContCotizacionesEquipo(
					getAccionFuncionalidad(), (AppDemeter) app);
			servicio.setIdFuncionalidad(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IAplicacion getApp() {
		return app;
	}

	public void setApp(IAplicacion arg0) {
		this.app = (IZkAplicacion) arg0;
	}

	public Map<String, Object> getParametros() {
		// TODO Auto-generated method stub
		return mapa;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.mapa = parametros;
	}

	public void agregarParametro(String codigo, Object valor) {
		mapa.put(codigo, valor);

	}

	public void setAccionFuncionalidad(AccionFuncionalidad funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object seleccionar(int arg0) {
		return null;
	}

	public AccionFuncionalidad getAccionFuncionalidad() {
		return funcionalidades;
	}

}