package cpc.demeter.controlador.ministerio;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import cpc.ares.modelo.Accion;
import cpc.demeter.AppDemeter;
import cpc.demeter.catalogo.ministerio.ContTiposRiego;
import cpc.demeter.vista.ministerio.UiTipoRiego;
import cpc.modelo.ministerio.basico.TipoRiego;
import cpc.negocio.ministerio.basico.NegocioTipoRiego;
import cpc.zk.componente.excepciones.ExcDatosInvalidos;
import cpc.zk.componente.listas.ContVentanaBase;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.excepciones.ExcSeleccionNoValida;

public class ContTipoRiego extends ContVentanaBase<TipoRiego> {

	private static final long serialVersionUID = 6184414588153046382L;
	private NegocioTipoRiego servicio;
	private AppDemeter app;
	private UiTipoRiego vista;

	public ContTipoRiego(int modoOperacion, TipoRiego tipo,
			ContTiposRiego llamador, AppDemeter app) throws ExcDatosInvalidos,
			ExcSeleccionNoValida {
		super(modoOperacion);
		this.app = app;
		try {
			if (tipo == null || modoAgregar())
				tipo = new TipoRiego();
			setear(tipo, new UiTipoRiego("RIEGO ("
					+ Accion.TEXTO[modoOperacion] + ")", 550), llamador, app);
			vista = ((UiTipoRiego) getVista());
			vista.desactivar(modoOperacion);
		} catch (ExcDatosInvalidos e) {
			e.printStackTrace();
			this.app.mostrarError("No ha seleccionado ningun campo");
		}
	}

	public void eliminar() {

		try {
			servicio = NegocioTipoRiego.getInstance();
			servicio.setModelo(getDato());
			servicio.borrar();
		} catch (Exception e) {
			e.printStackTrace();
			app.mostrarError(e.getMessage());
		}
	}

	public void guardar() {

		try {
			servicio = NegocioTipoRiego.getInstance();
			servicio.setModelo(getDato());
			servicio.guardar(getDato().getId());
		} catch (Exception e) {
			e.printStackTrace();
			app.mostrarError(e.getMessage());
		}
	}

	public void validar() throws WrongValuesException, ExcEntradaInconsistente {
		if (getModoOperacion() != Accion.ELIMINAR
				&& (vista.getDescripcion().getValue() == null || vista
						.getDescripcion().getValue() == "")) {
			throw new WrongValueException(vista.getDescripcion(),
					"Indique una Descripcion");
		}
	}

	public void onEvent(Event arg0) throws Exception {
		validar();
		procesarCRUD(arg0);

	}

	@Override
	public void anular() throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public void correjir() throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesar() throws WrongValuesException, ExcEntradaInconsistente {
		// TODO Auto-generated method stub

	}

}
