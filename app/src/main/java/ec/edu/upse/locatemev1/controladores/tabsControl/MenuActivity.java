package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.controladores.principal.MainActivity;
import ec.edu.upse.locatemev1.controladores.usuarioTutorControl.PerfilUsuarioTutor;
import layout.UbicacionFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new MenuFragment()).commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_inicio) {
            //Intent intent = new Intent(getApplication(),MenuActivity.class);
            //startActivity(intent);

           fragmentManager.beginTransaction().replace(R.id.contenedor,new MenuFragment()).commit();

            // Handle the camera action
        } else if (id == R.id.nav_tutor) {
            Intent intent = new Intent(MenuActivity.this,PerfilUsuarioTutor.class);
            startActivity(intent);

            //fragmentManager.beginTransaction().replace(R.id.contenedor,new AlertasFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_alertas) {
            //Intent intent = new Intent(MenuActivity.this,MapsActivity.class);
           // startActivity(intent);

        } else if (id == R.id.nav_mapa) {
            UbicacionFragment ubicacionFragment = new UbicacionFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, ubicacionFragment).commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_cerrar_sesion) {

            final ProgressDialog dialogo = new ProgressDialog(MenuActivity.this, R.style.AppTheme);
            dialogo.setIndeterminate(true);
            dialogo.setMessage("Cerrando Sesion...");
            dialogo.show();

            long delayInMillis = 5000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dialogo.dismiss();
                    SharedPreferences miPreferencia = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = miPreferencia.edit();
                    finish();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }, delayInMillis);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // Toast.makeText(MenuActivity.this, "onResume", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
       // Toast.makeText(MenuActivity.this, "onStop", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(MenuActivity.this, "onRestart", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       //Toast.makeText(MenuActivity.this, "onDestroy", Toast.LENGTH_LONG).show();

    }
}
