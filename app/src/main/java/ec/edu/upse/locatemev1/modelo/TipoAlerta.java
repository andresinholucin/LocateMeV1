package ec.edu.upse.locatemev1.modelo;
/**
 * Created by Mishell on 26/07/2017.
 */

public class TipoAlerta {
    private Integer idtipoAlerta;
    private String usuTipoalertaDescripcion;
    private String usuTipoalertaEstado;

    public TipoAlerta() {
    }

    public TipoAlerta(Integer idtipoAlerta, String usuTipoalertaDescripcion, String usuTipoalertaEstado) {
        this.idtipoAlerta = idtipoAlerta;
        this.usuTipoalertaDescripcion = usuTipoalertaDescripcion;
        this.usuTipoalertaEstado = usuTipoalertaEstado;
    }

    public Integer getIdtipoAlerta() {
        return idtipoAlerta;
    }

    public void setIdtipoAlerta(Integer idtipoAlerta) {
        this.idtipoAlerta = idtipoAlerta;
    }

    public String getUsuTipoalertaDescripcion() {
        return usuTipoalertaDescripcion;
    }

    public void setUsuTipoalertaDescripcion(String usuTipoalertaDescripcion) {
        this.usuTipoalertaDescripcion = usuTipoalertaDescripcion;
    }

    public String getUsuTipoalertaEstado() {
        return usuTipoalertaEstado;
    }

    public void setUsuTipoalertaEstado(String usuTipoalertaEstado) {
        this.usuTipoalertaEstado = usuTipoalertaEstado;
    }
}
