package cpc.demeter.controlador.mantenimiento;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;

import cpc.ares.modelo.Accion;
import cpc.demeter.AppDemeter;
import cpc.demeter.catalogo.mantenimiento.ContConsumibles;
import cpc.demeter.vista.UiBanco;
import cpc.demeter.vista.mantenimiento.UiConsumible;

import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.mantenimiento.Consumible;
import cpc.modelo.demeter.mantenimiento.ConsumibleEquivalente;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.negocio.demeter.mantenimiento.NegocioConsumible;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumible;

import cpc.zk.componente.excepciones.ExcDatosInvalidos;
import cpc.zk.componente.interfaz.ICompCatalogo;
import cpc.zk.componente.listas.CompBuscar;
import cpc.zk.componente.listas.CompGrilla;
import cpc.zk.componente.listas.CompGrillaConBoton;
import cpc.zk.componente.listas.ContVentanaBase;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.excepciones.ExcSeleccionNoValida;

public class ContConsumible extends ContVentanaBase<Consumible> {

	private static final long serialVersionUID = 6184414588153046382L;
	private NegocioConsumible servicio;
	private AppDemeter app;
	private UiConsumible vista;

	public ContConsumible(int modoOperacion, Consumible consumible,
			ICompCatalogo<Consumible> llamador, AppDemeter app)
			throws ExcDatosInvalidos, ExcSeleccionNoValida {
		super(modoOperacion);
		this.app = app;

		try {
			if (consumible == null || modoAgregar()) {
				consumible = new Consumible();

			}
			setDato(consumible);
			setear(getDato(), new UiConsumible("sss", 1100, modoOperacion,
					consumible, consumible.getConsumibleEquivalente()),
					llamador, app);
			vista = ((UiConsumible) getVista());
			vista.desactivar(getModoOperacion());
			// prueba();

		} catch (ExcDatosInvalidos e) {
			e.printStackTrace();
			this.app.mostrarError("No ha seleccionado ningun campo");
		}

	}

	@SuppressWarnings("unchecked")
	public void prueba() {
		List<Row> a = vista.getDetalle().getFilas().getChildren();
		for (Row row : a) {

			CompBuscar<ConsumibleEquivalente> z = (CompBuscar<ConsumibleEquivalente>) row
					.getChildren().get(0);
			// z.setText(z.getSeleccion().toString());

		}
	}

	public void eliminar() {

		try {
			servicio = NegocioConsumible.getInstance();
			servicio.setConsumible(getDato());
			// servicio.eliminar();
		} catch (Exception e) {
			e.printStackTrace();
			app.mostrarError(e.getMessage());
		}
	}

	public void guardar() {

		try {
			servicio = NegocioConsumible.getInstance();
			List<ConsumibleEquivalente> za = new ArrayList<ConsumibleEquivalente>();
			if (modoEditar()) {
				List<ArticuloVenta> z = getArticuloVentas();

				if (getDato().getConsumibleEquivalente() != null) {
					za = getDato().getConsumibleEquivalente();
				}

				for (ArticuloVenta articulo : z) {
					ConsumibleEquivalente equivalente = new ConsumibleEquivalente();
					Consumible consumible = servicio
							.getConsumibleArticulo(articulo);

					equivalente.setConsumibleEq(consumible);
					za.add(equivalente);

				}

				getDato().setConsumibleEquivalente(za);

			}
			servicio.setConsumible(getDato());
			servicio.guardar(getDato());

		} catch (Exception e) {
			e.printStackTrace();
			app.mostrarError(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<ArticuloVenta> getArticuloVentas() {
		List<ArticuloVenta> za = new ArrayList<ArticuloVenta>();
		List<Row> filas = vista.getDetalle().getFilas().getChildren();
		for (Row row : filas) {
			CompBuscar<ArticuloVenta> a = (CompBuscar<ArticuloVenta>) row
					.getChildren().get(0);
			za.add(a.getSeleccion());
		}

		return za;

	}

	public void validar() throws WrongValuesException, ExcEntradaInconsistente {
		/*
		 * if (getModoOperacion()!= Accion.ELIMINAR &&
		 * (vista.getDescripcion().getValue()==null ||
		 * vista.getDescripcion().getValue()=="")){ throw new
		 * WrongValueException(((UiBanco)getVista()).getDescripcion()
		 * ,"Indique una Descripcion"); }
		 */

		// validarDetalle();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void validarDetalle() throws WrongValuesException,
			ExcEntradaInconsistente {
		List<Row> referencia = vista.getDetalle().getFilas().getChildren();
		List<Row> comparador = vista.getDetalle().getFilas().getChildren();
		Integer contador;

		for (Row row : referencia) {
			contador = 0;
			CompBuscar<Consumible> basico = (CompBuscar<Consumible>) row
					.getChildren().get(0);
			for (Row row2 : referencia) {
				// z.setText(z.getSeleccion().toString());

				CompBuscar<Consumible> compara = (CompBuscar<Consumible>) row
						.getChildren().get(0);
				if (basico.getSeleccion() == compara.getSeleccion())
					contador++;
				if (contador >= 2) {
					throw new WrongValueException(basico,
							"El Consumible fue indicado mas de una ves");

				}

			}

		}
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(CompBuscar.ON_SELECCIONO)) {
			if (event.getTarget() == vista.getCmbarticulo()) {
				getDato().setArticuloVenta(
						vista.getCmbarticulo().getSeleccion());
				vista.getTxtCodCCNU().setText(
						vista.getCmbarticulo().getSeleccion().getCodigoCCNU());
				vista.getTxtCodFabricante().setText(
						vista.getCmbarticulo().getSeleccion()
								.getCodigoFabricante());
				vista.getTxtCodSigesp()
						.setText(
								vista.getCmbarticulo().getSeleccion()
										.getCodigoSIGESP());
				vista.getTxtDenFabricante().setText(
						vista.getCmbarticulo().getSeleccion()
								.getDenominacionFabricante());

			} else {
				CompBuscar<ArticuloVenta> componente = (CompBuscar<ArticuloVenta>) event
						.getTarget();

				actualizardatosarticulodetalle(componente);
			}
		}

		else if (event.getTarget() == getVista().getAceptar()) {
			validar();
			procesarCRUD(event);

		}

	}

	public void actualizardatosarticulodetalle(
			CompBuscar<ArticuloVenta> componente) throws ExcFiltroExcepcion {
		try {
			Row registro = (Row) componente.getParent();
			((Label) registro.getChildren().get(1)).setValue(componente
					.getValorObjeto().getDenominacionFabricante());
			((Label) registro.getChildren().get(2)).setValue(componente
					.getValorObjeto().getCodigoSIGESP());
			((Label) registro.getChildren().get(3)).setValue(componente
					.getValorObjeto().getCodigoFabricante());
			((Label) registro.getChildren().get(3)).setValue(componente
					.getValorObjeto().getCodigoCCNU());
		} catch (Exception e) {
			throw new ExcFiltroExcepcion("Problemas con la Maquinaria");
		}
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
