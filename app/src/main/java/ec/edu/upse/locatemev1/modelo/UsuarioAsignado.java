package ec.edu.upse.locatemev1.modelo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gitwyn_pc on 13/12/2017.
 */

public class UsuarioAsignado implements Parcelable {
    private Long idusuarioAsignado;
    private String estado;
    private Usuario usuario1;
    private Usuario usuario2;

    public UsuarioAsignado() {
    }

    public Long getIdusuarioAsignado() {
        return idusuarioAsignado;
    }

    public void setIdusuarioAsignado(Long idusuarioAsignado) {
        this.idusuarioAsignado = idusuarioAsignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idusuarioAsignado);
        dest.writeString(this.estado);
        dest.writeParcelable(this.usuario1, flags);
        dest.writeParcelable(this.usuario2, flags);
    }

    protected UsuarioAsignado(Parcel in) {
        this.idusuarioAsignado = (Long) in.readValue(Long.class.getClassLoader());
        this.estado = in.readString();
        this.usuario1 = in.readParcelable(Usuario.class.getClassLoader());
        this.usuario2 = in.readParcelable(Usuario.class.getClassLoader());
    }

    public static final Parcelable.Creator<UsuarioAsignado> CREATOR = new Parcelable.Creator<UsuarioAsignado>() {
        @Override
        public UsuarioAsignado createFromParcel(Parcel source) {
            return new UsuarioAsignado(source);
        }

        @Override
        public UsuarioAsignado[] newArray(int size) {
            return new UsuarioAsignado[size];
        }
    };

    @Override
    public String toString() {
        return "UsuarioAsignado{" +
                "idusuarioAsignado=" + idusuarioAsignado +
                ", estado='" + estado + '\'' +
                ", usuario1=" + usuario1 +
                ", usuario2=" + usuario2 +
                '}';
    }
}
