package com.resulkacar.turquoise;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class kayit_ol extends AppCompatActivity {

    static String TAG="evcilhayvansahiplendirme";


    private static String ip="192.168.170.75";
    private static String port ="1433";
    private static String Classs="net.sourceforge.jtds.jdbc.Driver";
    private static String database="evcilhayvansahiplendirme";
    private static String username="sa";
    private static String password="1";
    private static String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private Connection connection=null;



    Button cinsiyet_button,sehir_button,dogumt_button,meslek_button,kayitol_button;
    EditText kayitol_kullaniciadi,kayitol_kullanicisifre,kayitol_email,kayitol_telefon;
    TextView kayitol_logo;

    String vt_cinsiyet,vt_sehir,vt_dtarihi,vt_meslek;
    LocalDate vt_dtarih ;
    java.sql.Date sqlDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);


        kayitol_kullaniciadi = findViewById(R.id.profilduzenle_kullaniciadi);
        kayitol_kullanicisifre = findViewById(R.id.profilduzenle_kullanicisifre);
        kayitol_email = findViewById(R.id.profilduzenle_email);
        kayitol_telefon = findViewById(R.id.profilduzenle_telefon);
        cinsiyet_button = findViewById(R.id.cinsiyet_button);
        sehir_button = findViewById(R.id.sehir_button);
        dogumt_button = findViewById(R.id.dogumt_button);
        meslek_button = findViewById(R.id.meslek_button);
        kayitol_button = findViewById(R.id.profilduzenle_guncelle);
        kayitol_logo = findViewById(R.id.kayitol_logo);

        kayitol_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baglan();
                kayitOl();

            }
        });

        cinsiyet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cinsiyet_vt();
            }
        });

        sehir_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sehir_vt();

            }
        });

        dogumt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dogumt_vt();

            }
        });

        meslek_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                meslek_vt();

            }
        });




    }

    void cinsiyet_vt() {

        PopupMenu popupMenu = new PopupMenu(kayit_ol.this, cinsiyet_button);

        popupMenu.getMenuInflater().inflate(R.menu.cinsiyet_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(kayit_ol.this, "Seçilen" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                cinsiyet_button.setText(menuItem.getTitle());
                vt_cinsiyet = menuItem.getTitle().toString();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    void sehir_vt() {

        PopupMenu popupMenu = new PopupMenu(kayit_ol.this, sehir_button);

        popupMenu.getMenuInflater().inflate(R.menu.sehir_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(kayit_ol.this, "Seçilen" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                sehir_button.setText(menuItem.getTitle());
                vt_sehir = menuItem.getTitle().toString();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    void dogumt_vt()
    {


        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                kayit_ol.this,
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        dogumt_button.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        vt_dtarih = LocalDate.of(year,(monthOfYear+1),dayOfMonth);
                        sqlDate= java.sql.Date.valueOf(String.valueOf(vt_dtarih));
                        vt_dtarihi = year+"-" + (monthOfYear+1) + "-" + dayOfMonth;

                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);
        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show();
    }
    void meslek_vt() {

        PopupMenu popupMenu = new PopupMenu(kayit_ol.this, meslek_button);

        popupMenu.getMenuInflater().inflate(R.menu.meslek, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(kayit_ol.this, "Seçilen" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                meslek_button.setText(menuItem.getTitle());
                vt_meslek = menuItem.getTitle().toString();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

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


    public void kayitOl()  {

        try {
            PreparedStatement preparedStatement  = connection.prepareStatement("INSERT INTO " + "kullanici_tablosu " +
                    "(kullanici_adi,kullanici_sifre,kullanici_email,kullanici_telefon,kullanici_cinsiyet,kullanici_sehir,kullanici_dtarihi,kullanici_meslek)  VALUES (?,?,?,?,?,?,?,?)");

            PreparedStatement preparedStatement1  = connection.prepareStatement("INSERT INTO " + "kullanici_bilgileri" +
                    "(adi,soyadi,adresi,hakkinda)  VALUES (?,?,?,?)");
            try {
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO kullanici_gonderi(fotograf) VALUES(?)");

                // Profil resmini byte dizisine çevir
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = kayitol_logo.getDrawingCache();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // ? işaretlerine değerleri yerleştir
                preparedStatement2.setBytes(1, byteArray);

                // Sorguyu çalıştır
                preparedStatement2.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }



            preparedStatement1.setString(1,kayitol_kullaniciadi.getText().toString());
            preparedStatement1.setString(2,sehir_button.getText().toString());
            preparedStatement1.setString(3,sehir_button.getText().toString());
            preparedStatement1.setString(4,meslek_button.getText().toString());
            preparedStatement1.execute();


            preparedStatement.setString(1, kayitol_kullaniciadi.getText().toString());
            preparedStatement.setString(2,kayitol_kullanicisifre.getText().toString());
            preparedStatement.setString(3,kayitol_email.getText().toString());
            preparedStatement.setString(4,kayitol_telefon.getText().toString());
            preparedStatement.setString(5,vt_cinsiyet.toString());
            preparedStatement.setString(6,vt_sehir.toString());
            preparedStatement.setDate(7,sqlDate);
            preparedStatement.setString(8,vt_meslek.toString());

            Toast.makeText(kayit_ol.this,"Başarıyla Kayıt Olundu",Toast.LENGTH_LONG).show();

            preparedStatement.execute();

            Intent intent = new Intent(kayit_ol.this, MainActivity.class);
            startActivity(intent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}