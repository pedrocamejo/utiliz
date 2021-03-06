package cpc.demeter.comando.ministerio;

import java.util.Hashtable;
import java.util.Map;

import cpc.ares.interfaz.IAplicacion;
import cpc.ares.interfaz.IComando;
import cpc.ares.modelo.AccionFuncionalidad;
import cpc.demeter.AppDemeter;
import cpc.demeter.catalogo.ContUnidadesMedida;
import cpc.zk.componente.interfaz.IZkAplicacion;

public class ComandoUnidadMedida implements IComando {

	private static final long serialVersionUID = -2688081335887322152L;
	private AccionFuncionalidad funcionalidades;
	private Map<String, Object> mapa = new Hashtable<String, Object>();
	private int id;
	private IZkAplicacion app;

	public void ejecutar() {
		try {
			ContUnidadesMedida servicio = new ContUnidadesMedida(
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

	public AccionFuncionalidad getAccionFuncionalidad() {

		return funcionalidades;
	}

	public Object seleccionar(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
