package ec.edu.upse.locatemev1.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Gitwym on 25/07/2017.
 */

public class Usuario implements Parcelable {
    private Long idusuario;
    private String usuUAnio;
    private String usuUApellidos;
    private String usuUCedula;
    private String usuUClave;
    private String usuUCorreo;
    private String usuUDia;
    private String usuUDireccion;
    private String usuUEstado;
    private String usuUMes;
    private String usuUNombres;
    private String usuUSms;
    private String usuUTelefono;
    private String usuUUsuario;

    private List<UbicacionUsuario> ubicacionUsuarios;
    private List<EmisionAlerta> emisionAlertas;

    private TipoDiscapacidad tipoDiscapacidad;
    private TiempoSensado tiempoSensado;
    private Perimetro perimetroSensado;

    public Usuario() {
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuUAnio() {
        return usuUAnio;
    }

    public void setUsuUAnio(String usuUAnio) {
        this.usuUAnio = usuUAnio;
    }

    public String getUsuUApellidos() {
        return usuUApellidos;
    }

    public void setUsuUApellidos(String usuUApellidos) {
        this.usuUApellidos = usuUApellidos;
    }

    public String getUsuUCedula() {
        return usuUCedula;
    }

    public void setUsuUCedula(String usuUCedula) {
        this.usuUCedula = usuUCedula;
    }

    public String getUsuUClave() {
        return usuUClave;
    }

    public void setUsuUClave(String usuUClave) {
        this.usuUClave = usuUClave;
    }

    public String getUsuUCorreo() {
        return usuUCorreo;
    }

    public void setUsuUCorreo(String usuUCorreo) {
        this.usuUCorreo = usuUCorreo;
    }

    public String getUsuUDia() {
        return usuUDia;
    }

    public void setUsuUDia(String usuUDia) {
        this.usuUDia = usuUDia;
    }

    public String getUsuUDireccion() {
        return usuUDireccion;
    }

    public void setUsuUDireccion(String usuUDireccion) {
        this.usuUDireccion = usuUDireccion;
    }

    public String getUsuUEstado() {
        return usuUEstado;
    }

    public void setUsuUEstado(String usuUEstado) {
        this.usuUEstado = usuUEstado;
    }

    public String getUsuUMes() {
        return usuUMes;
    }

    public void setUsuUMes(String usuUMes) {
        this.usuUMes = usuUMes;
    }

    public String getUsuUNombres() {
        return usuUNombres;
    }

    public void setUsuUNombres(String usuUNombres) {
        this.usuUNombres = usuUNombres;
    }

    public String getUsuUSms() {
        return usuUSms;
    }

    public void setUsuUSms(String usuUSms) {
        this.usuUSms = usuUSms;
    }

    public String getUsuUTelefono() {
        return usuUTelefono;
    }

    public void setUsuUTelefono(String usuUTelefono) {
        this.usuUTelefono = usuUTelefono;
    }

    public String getUsuUUsuario() {
        return usuUUsuario;
    }

    public void setUsuUUsuario(String usuUUsuario) {
        this.usuUUsuario = usuUUsuario;
    }

    public List<UbicacionUsuario> getUbicacionUsuarios() {
        return ubicacionUsuarios;
    }

    public void setUbicacionUsuarios(List<UbicacionUsuario> ubicacionUsuarios) {
        this.ubicacionUsuarios = ubicacionUsuarios;
    }

    public List<EmisionAlerta> getEmisionAlertas() {
        return emisionAlertas;
    }

    public void setEmisionAlertas(List<EmisionAlerta> emisionAlertas) {
        this.emisionAlertas = emisionAlertas;
    }
    public TipoDiscapacidad getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public TiempoSensado getTiempoSensado() {
        return tiempoSensado;
    }

    public void setTiempoSensado(TiempoSensado tiempoSensado) {
        this.tiempoSensado = tiempoSensado;
    }

    public Perimetro getPerimetroSensado() {
        return perimetroSensado;
    }

    public void setPerimetroSensado(Perimetro perimetroSensado) {
        this.perimetroSensado = perimetroSensado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idusuario=" + idusuario +
                ", usuUAnio='" + usuUAnio + '\'' +
                ", usuUApellidos='" + usuUApellidos + '\'' +
                ", usuUCedula='" + usuUCedula + '\'' +
                ", usuUClave='" + usuUClave + '\'' +
                ", usuUCorreo='" + usuUCorreo + '\'' +
                ", usuUDia='" + usuUDia + '\'' +
                ", usuUDireccion='" + usuUDireccion + '\'' +
                ", usuUEstado='" + usuUEstado + '\'' +
                ", usuUMes='" + usuUMes + '\'' +
                ", usuUNombres='" + usuUNombres + '\'' +
                ", usuUSms='" + usuUSms + '\'' +
                ", usuUTelefono='" + usuUTelefono + '\'' +
                ", usuUUsuario='" + usuUUsuario + '\'' +
                ", tipoDiscapacidad=" + tipoDiscapacidad +
                ", tiempoSensado=" + tiempoSensado +
                ", perimetroSensado=" + perimetroSensado +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idusuario);
        dest.writeString(this.usuUAnio);
        dest.writeString(this.usuUApellidos);
        dest.writeString(this.usuUCedula);
        dest.writeString(this.usuUClave);
        dest.writeString(this.usuUCorreo);
        dest.writeString(this.usuUDia);
        dest.writeString(this.usuUDireccion);
        dest.writeString(this.usuUEstado);
        dest.writeString(this.usuUMes);
        dest.writeString(this.usuUNombres);
        dest.writeString(this.usuUSms);
        dest.writeString(this.usuUTelefono);
        dest.writeString(this.usuUUsuario);
        dest.writeParcelable(this.tipoDiscapacidad, flags);
        dest.writeParcelable(this.tiempoSensado, flags);
        dest.writeParcelable(this.perimetroSensado, flags);
    }

    protected Usuario(Parcel in) {
        this.idusuario = (Long) in.readValue(Integer.class.getClassLoader());
        this.usuUAnio = in.readString();
        this.usuUApellidos = in.readString();
        this.usuUCedula = in.readString();
        this.usuUClave = in.readString();
        this.usuUCorreo = in.readString();
        this.usuUDia = in.readString();
        this.usuUDireccion = in.readString();
        this.usuUEstado = in.readString();
        this.usuUMes = in.readString();
        this.usuUNombres = in.readString();
        this.usuUSms = in.readString();
        this.usuUTelefono = in.readString();
        this.usuUUsuario = in.readString();
        this.tipoDiscapacidad = in.readParcelable(TipoDiscapacidad.class.getClassLoader());
        this.tiempoSensado = in.readParcelable(TiempoSensado.class.getClassLoader());
        this.perimetroSensado = in.readParcelable(Perimetro.class.getClassLoader());
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

}
