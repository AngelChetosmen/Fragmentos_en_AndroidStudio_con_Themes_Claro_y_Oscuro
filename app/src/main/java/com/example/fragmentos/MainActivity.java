package com.example.fragmentos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.preference.PreferenceManager;
import android.widget.Button;
import android.content.SharedPreferences;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Button btn_change_theme;
    private boolean isDarkTheme = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Establecer el tema antes de setContentView
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean useDarkTheme = prefs.getBoolean("DarkTheme", false);
        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        setContentView(R.layout.activity_main);

        Button btnChangeTheme = findViewById(R.id.btn_change_theme);
        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar el valor del tema y reiniciar la actividad
                isDarkTheme = !isDarkTheme;
                changeTheme();
                boolean useDarkTheme = prefs.getBoolean("DarkTheme", false);
                prefs.edit().putBoolean("DarkTheme", !useDarkTheme).apply();
                recreate();
            }
        });
        // Agrega el fragmento de encabezado
        getSupportFragmentManager().beginTransaction()
                .add(R.id.header_container, new Encabezado())
                .commit();

        // Agrega el fragmento de pie
        getSupportFragmentManager().beginTransaction()
                .add(R.id.footer_container, new PiePag())
                .commit();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void setAppTheme(){
        if (isDarkTheme){
            setTheme(R.style.AppTheme_Dark);
        }else {
            setTheme(R.style.AppTheme_Light);
        }
    }
    private void changeTheme(){
        //Guarda el estado actual del tema
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("DARK_THEME", isDarkTheme);
        editor.apply();

        //Restaura la actividad para aplicar el tema
        recreate();
    }
}