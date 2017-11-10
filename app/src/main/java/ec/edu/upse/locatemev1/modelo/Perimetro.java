package ec.edu.upse.locatemev1.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gitwyn_pc on 05/10/2017.
 */

public class Perimetro implements Parcelable {
    private int idperimetroSensado;
    private String usuPerimetroDescripcion;
    private String usuPerimetroEstado;

    public int getIdperimetroSensado() {
        return idperimetroSensado;
    }

    public void setIdperimetroSensado(int idperimetroSensado) {
        this.idperimetroSensado = idperimetroSensado;
    }

    public String getUsuPerimetroDescripcion() {
        return usuPerimetroDescripcion;
    }

    public void setUsuPerimetroDescripcion(String usuPerimetroDescripcion) {
        this.usuPerimetroDescripcion = usuPerimetroDescripcion;
    }

    public String getUsuPerimetroEstado() {
        return usuPerimetroEstado;
    }

    public void setUsuPerimetroEstado(String usuPerimetroEstado) {
        this.usuPerimetroEstado = usuPerimetroEstado;
    }

    @Override
    public String toString() {
        return "{" +
                "idperimetroSensado=" + idperimetroSensado +
                ", usuPerimetroDescripcion='" + usuPerimetroDescripcion + '\'' +
                ", usuPerimetroEstado='" + usuPerimetroEstado + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idperimetroSensado);
        dest.writeString(this.usuPerimetroDescripcion);
        dest.writeString(this.usuPerimetroEstado);
    }

    public Perimetro() {
    }

    protected Perimetro(Parcel in) {
        this.idperimetroSensado = in.readInt();
        this.usuPerimetroDescripcion = in.readString();
        this.usuPerimetroEstado = in.readString();
    }

    public static final Creator<Perimetro> CREATOR = new Creator<Perimetro>() {
        @Override
        public Perimetro createFromParcel(Parcel source) {
            return new Perimetro(source);
        }

        @Override
        public Perimetro[] newArray(int size) {
            return new Perimetro[size];
        }
    };
}
