package cpc.demeter.comando.mantenimiento;

import java.util.Hashtable;
import java.util.Map;

import cpc.ares.interfaz.IAplicacion;
import cpc.ares.interfaz.IComando;
import cpc.ares.modelo.AccionFuncionalidad;
import cpc.demeter.AppDemeter;
import cpc.demeter.catalogo.mantenimiento.ContTiposGarantias;
import cpc.zk.componente.excepciones.ExcColumnasInvalidas;
import cpc.zk.componente.interfaz.IZkAplicacion;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class ComandoTipoGarantia implements IComando {

	private static final long serialVersionUID = 1L;
	private AccionFuncionalidad funcionalidades;
	private Map<String, Object> mapa = new Hashtable<String, Object>();
	private int id;
	private IZkAplicacion app;

	public void ejecutar() {
		try {

			ContTiposGarantias servicio = new ContTiposGarantias(
					getAccionFuncionalidad(), (AppDemeter) app);
			servicio.setIdFuncionalidad(id);

		} catch (ExcColumnasInvalidas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcArgumentoInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
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
