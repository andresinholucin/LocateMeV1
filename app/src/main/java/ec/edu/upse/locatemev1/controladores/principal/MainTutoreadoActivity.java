package ec.edu.upse.locatemev1.controladores.principal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ec.edu.upse.locatemev1.R;

public class MainTutoreadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tutoreado);
    }

    public void Alertas(View view){
        Toast.makeText(MainTutoreadoActivity.this," Alerta !!!", Toast.LENGTH_LONG).show();
    }

    public void CerrarSesion(View view){

        SharedPreferences miPreferencia = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        finish();
        editor.clear();
        editor.commit();

        final ProgressDialog dialogo = new ProgressDialog(MainTutoreadoActivity.this, R.style.AppTheme);
        dialogo.setIndeterminate(true);
        dialogo.setMessage("Cerrando Sesion...");
        dialogo.show();

        long delayInMillis = 5000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialogo.dismiss();
            }
        }, delayInMillis);
        //Intent intent = new Intent(MainTutoreadoActivity.this,MenuActivity.class);
        //startActivity(intent);
    }

}
