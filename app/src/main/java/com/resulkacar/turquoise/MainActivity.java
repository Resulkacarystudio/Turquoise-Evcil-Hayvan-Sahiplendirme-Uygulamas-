package com.resulkacar.turquoise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String sunucuadresi, veritabani, vtkuladi, vtsifre;
    int vtID;
    static String TAG = "evcilhayvansahiplendirme";


    private static String ip = "192.168.170.75";
    private static String port = "1433";
    private static String Classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "evcilhayvansahiplendirme";
    private static String username = "sa";
    private static String password = "1";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    private Connection connection = null;


    Button main_kayitol_button, main_giris_button;
    EditText main_kadi_txt, main_ksifre_txt;
    TextView main_projeismi_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // FULLSCREEN özelliği öncesinde çağrılmalı
        requestWindowFeature(Window.FEATURE_NO_TITLE); // <-- ilk satır

        // Ekranı tam ekran yap
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState); // <-- sonra super.onCreate()

        setContentView(R.layout.activity_main); // <-- layout buradan sonra eklenir

        // findViewById çağrıları setContentView'den sonra olmalı
        main_kayitol_button = findViewById(R.id.kayit_ol_main_button);
        main_giris_button = findViewById(R.id.girisyap_main_button);
        main_kadi_txt = findViewById(R.id.main_kullaniciadi_txt);
        main_ksifre_txt = findViewById(R.id.main_kullanicisifre_txt);
        main_projeismi_txt = findViewById(R.id.projeismi_txt);

        // Modern Android için status bar gizleme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );
            }
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        main_kayitol_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, kayit_ol.class);
                startActivity(intent);
            }
        });

        main_giris_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baglan();
                girisYap();
            }
        });
    }

    public void baglan() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(Classs);
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }


    public void girisYap() {

        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from kullanici_tablosu where kullanici_adi=" + "'" + main_kadi_txt.getText().toString() + "'" + ";");






                if (resultSet.next()) {
                    String vtkadi = resultSet.getString("kullanici_adi").trim();
                    String vtksifre = resultSet.getString("kullanici_sifre").trim();
                    String vtkID = resultSet.getString("ID").trim();



                    if (vtkadi.equals(main_kadi_txt.getText().toString())) {
                        if (vtksifre.equals(main_ksifre_txt.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Giriş Yaptınız.", Toast.LENGTH_SHORT).show();

                            //INSERT INTO ilk_tablo (id, isim, soyisim) VALUES (3, 'John', 'Doe');
                            //INSERT INTO ikinci_tablo (id, telefon) VALUES (3, '555-1234');



                            sharedPreferences= this.getSharedPreferences("com.resulkacar.turquoise",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("vtID", Integer.parseInt(vtkID));
                            editor.apply();


                          Intent intent = new Intent(MainActivity.this, anasayfa.class);
                          startActivity(intent);


                        } else {
                            Toast.makeText(MainActivity.this, "Şifreniz Yanlış.", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Kullanıcızı adınızı kontrol ediniz.", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(MainActivity.this, "kayıt yok", Toast.LENGTH_SHORT).show();
                }

            } catch (SQLException e) {
                main_projeismi_txt.setText("btra");
                System.out.println("hata" + e);
                throw new RuntimeException(e);
            }


        } else {

            main_projeismi_txt.setText("Bağlantı boş");
        }


    }
}





