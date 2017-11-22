package ec.edu.upse.locatemev1.configuracion;

import android.app.Application;

import java.util.List;

import ec.edu.upse.locatemev1.modelo.EmisionAlerta;
import ec.edu.upse.locatemev1.modelo.Perimetro;
import ec.edu.upse.locatemev1.modelo.TiempoSensado;
import ec.edu.upse.locatemev1.modelo.TipoDiscapacidad;
import ec.edu.upse.locatemev1.modelo.Usuario;

/**
 * Created by Gitwym on 07/08/2017.
 */

public class VariablesGenerales extends Application {
    public static VariablesGenerales instance;
    //public static String strRuta="http://172.19.10.63:8080/WebServiceAlertasSpring/api/";
    public static Long lonIdUsuario;
    public static Long lonIdTutor;
    public static Long lonIdTutoriadosLista;
    public static Long lonIdUbicacion;
    public static Integer intRango;
    public static Integer intPeticionTutoriado;
    public static Integer intPeticionAlerta;
    public static List<Usuario> listUsuario;
    public static List<EmisionAlerta> listEmisionAlerta;

    public List<Usuario> listaUsuariosPorTutor;
    public List<TipoDiscapacidad> listaTipoDiscapacidad;
    public List<TiempoSensado> listaTiempoSensado;
    public List<Perimetro> listaPerimetro;

    public List<Usuario> getListaUsuariosPorTutor() {
        return listaUsuariosPorTutor;
    }

    public void setListaUsuariosPorTutor(List<Usuario> listaUsuariosPorTutor) {
        this.listaUsuariosPorTutor = listaUsuariosPorTutor;
    }

    public List<TipoDiscapacidad> getListaTipoDiscapacidad() {
        return listaTipoDiscapacidad;
    }

    public void setListaTipoDiscapacidad(List<TipoDiscapacidad> listaTipoDiscapacidad) {
        this.listaTipoDiscapacidad = listaTipoDiscapacidad;
    }

    public List<TiempoSensado> getListaTiempoSensado() {
        return listaTiempoSensado;
    }

    public void setListaTiempoSensado(List<TiempoSensado> listaTiempoSensado) {
        this.listaTiempoSensado = listaTiempoSensado;
    }

    public List<Perimetro> getListaPerimetro() {
        return listaPerimetro;
    }

    public void setListaPerimetro(List<Perimetro> listaPerimetro) {
        this.listaPerimetro = listaPerimetro;
    }



    public static List<EmisionAlerta> getListEmisionAlerta() {
        return listEmisionAlerta;
    }

    public static void setListEmisionAlerta(List<EmisionAlerta> listEmisionAlerta) {
        VariablesGenerales.listEmisionAlerta = listEmisionAlerta;
    }

    public static Long getLonIdUsuario() {
        return lonIdUsuario;
    }

    public static void setLonIdUsuario(Long lonIdUsuario) {
        VariablesGenerales.lonIdUsuario = lonIdUsuario;
    }

    public static Long getLonIdUbicacion() {
        return lonIdUbicacion;
    }

    public static void setLonIdUbicacion(Long lonIdUbicacion) {
        VariablesGenerales.lonIdUbicacion = lonIdUbicacion;
    }
    public static Long getLonIdTutor() {
        return lonIdTutor;
    }

    public static void setLonIdTutor(Long lonIdTutor) {
        VariablesGenerales.lonIdTutor = lonIdTutor;
    }

    public static List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public static void setListUsuario(List<Usuario> listUsuario) {
        VariablesGenerales.listUsuario = listUsuario;
    }

    public static Long getLonIdTutoriadosLista() {
        return lonIdTutoriadosLista;
    }

    public static void setLonIdTutoriadosLista(Long lonIdTutoriadosLista) {
        VariablesGenerales.lonIdTutoriadosLista = lonIdTutoriadosLista;
    }

    public static Integer getIntPeticionTutoriado() {
        return intPeticionTutoriado;
    }

    public static void setIntPeticionTutoriado(Integer intPeticionTutoriado) {
        VariablesGenerales.intPeticionTutoriado = intPeticionTutoriado;
    }

    public static Integer getIntPeticionAlerta() {
        return intPeticionAlerta;
    }

    public static void setIntPeticionAlerta(Integer intPeticionAlerta) {
        VariablesGenerales.intPeticionAlerta = intPeticionAlerta;
    }

    /**
     *Tutor
     */
    public static Integer intBandera;

    public static Integer getIntBandera() {
        return intBandera;
    }

    public static void setIntBandera(Integer intBandera) {
        VariablesGenerales.intBandera = intBandera;
    }

    public static List<Usuario> listTutor;

    public static List<Usuario> getListTutor() {
        return listTutor;
    }

    public static void setListTutor(List<Usuario> listTutor) {
        VariablesGenerales.listTutor = listTutor;
    }
}
