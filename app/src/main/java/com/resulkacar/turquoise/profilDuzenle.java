package com.resulkacar.turquoise;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;

public class profilDuzenle extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText profilduzenle_kullaniciadi,profilduzenle_kullanicisifre, profilduzenle_email, profilduzenle_telefonno, profilduzenle_ad, profilduzenle_soyad, profilduzenle_adres, profilduzenle_hakkinda;
    Button cinsiyet_btn,sehir_btn,dogumt,btn,meslek_btn,guncelle_btn;
    int anahtar;
    int hangisi=0;


    java.sql.Date sqlDate;




    String vtkadi;
    String vtksifre;
    String vtkemail;
    String vtktelefon;
    String vtad;
    String vtsoyad;
    String vtadres;
    String vthakkinda;
    String vtkcinsiyet;
    String vtksehir;
    String vtkdogumt;
    String vtkmeslek;
    LocalDate vt_dtarih ;






    static String TAG="evcilhayvansahiplendirme";


    private static String ip="192.168.170.75";
    private static String port ="1433";
    private static String Classs="net.sourceforge.jtds.jdbc.Driver";
    private static String database="evcilhayvansahiplendirme";
    private static String username="sa";
    private static String password="1";
    private static String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private Connection connection=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_duzenle);




        profilduzenle_kullaniciadi = findViewById(R.id.profilduzenle_kullaniciadi);
        profilduzenle_kullanicisifre = findViewById(R.id.profilduzenle_kullanicisifre);
        profilduzenle_email = findViewById(R.id.profilduzenle_email);
        profilduzenle_telefonno = findViewById(R.id.profilduzenle_telefon);
        profilduzenle_ad = findViewById(R.id.profilduzenle_ad);
        profilduzenle_soyad = findViewById(R.id.profilduzenle_soyad);
        profilduzenle_adres = findViewById(R.id.profilduzenle_adres);
        profilduzenle_hakkinda = findViewById(R.id.profilduzenle_hakkinda);
        cinsiyet_btn = findViewById(R.id.cinsiyet_button);
        sehir_btn = findViewById(R.id.sehir_button);
        dogumt = findViewById(R.id.dogumt_button);
        meslek_btn = findViewById(R.id.meslek_button);
        guncelle_btn = findViewById(R.id.profilduzenle_guncelle);



        SharedPreferences sharedPref = this.getSharedPreferences("com.resulkacar.turquoise", Context.MODE_PRIVATE);
        anahtar = sharedPref.getInt("vtID", 0);

        baglan();
        veriCek();

        cinsiyet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinsiyet_vt();
            }
        });

        sehir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sehir_vt();
            }
        });

        dogumt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogumt_vt();
            }
        });

        meslek_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meslek_vt();
            }
        });

        guncelle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baglan();
                profiliDuzenle();
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

    public void veriCek()
    {

        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from kullanici_tablosu JOIN kullanici_bilgileri ON kullanici_tablosu.ID = kullanici_bilgileri.ID where kullanici_tablosu.ID =" + anahtar +";");


                //SELECT *
                //FROM ilk_tablo
                //JOIN ikinci_tablo ON ilk_tablo.id = ikinci_tablo.id
                //WHERE ilk_tablo.id = 3;

                if (resultSet.next()) {
                     vtkadi = resultSet.getString("kullanici_adi").trim();
                     vtksifre = resultSet.getString("kullanici_sifre").trim();
                     vtkemail= resultSet.getString("kullanici_email").trim();
                     vtktelefon= resultSet.getString("kullanici_telefon").trim();
                     vtad =  resultSet.getString("adi").trim();
                     vtsoyad= resultSet.getString("soyadi").trim();
                     vtadres= resultSet.getString("adresi").trim();
                     vthakkinda = resultSet.getString("hakkinda").trim();
                     vtkcinsiyet= resultSet.getString("kullanici_cinsiyet");
                     vtksehir = resultSet.getString("kullanici_sehir").trim();
                     vtkdogumt= resultSet.getString("kullanici_dtarihi").trim();
                     vtkmeslek= resultSet.getString("kullanici_meslek").trim();



                    profilduzenle_kullaniciadi.setText(vtkadi);
                    profilduzenle_kullanicisifre.setText(vtksifre);
                    profilduzenle_email.setText(vtkemail);
                    profilduzenle_telefonno.setText(vtktelefon);
                    profilduzenle_ad.setText(vtad);
                    profilduzenle_soyad.setText(vtsoyad);
                    profilduzenle_adres.setText(vtadres);
                    profilduzenle_hakkinda.setText(vthakkinda);
                    cinsiyet_btn.setText(vtkcinsiyet);
                    sehir_btn.setText(vtksehir);
                    dogumt.setText(vtkdogumt);
                    meslek_btn.setText(vtkmeslek);



                }
            } catch (SQLException e) {

                System.out.println("hata" + e);
                throw new RuntimeException(e);
            }
            }
        }


        //burda kaldık
    public void profiliDuzenle()
    {
        if (connection != null) {
            Statement statement = null;
            try {

                String dt= (String) dogumt.getText();
                int[] dizi = new int[3];


                if(hangisi==0)
                {
                    // "-" karakterine göre ayırma
                    String[] parcalanan = dt.split("-");

                    // Elde edilen parçalı string'leri ekrana yazdırma
                    for (int i=0;i<=2;i++) {
                        dizi[i]= Integer.parseInt(parcalanan[i]);

                    }

                    vt_dtarih = LocalDate.of(dizi[0],(dizi[1]),dizi[2]);
                    sqlDate= java.sql.Date.valueOf(String.valueOf(vt_dtarih));


                }
                else if(hangisi==1)
                {

                }



                String kullaniciTablosuSQL = "UPDATE kullanici_tablosu SET  kullanici_adi = ?, kullanici_sifre=?,kullanici_email=?,kullanici_telefon=?,kullanici_cinsiyet=?,kullanici_sehir=?,kullanici_meslek=?,kullanici_dtarihi=? WHERE ID="+anahtar+" ;" ;

                try (PreparedStatement preparedStatement = connection.prepareStatement(kullaniciTablosuSQL)  ) {


                    preparedStatement.setString(1, profilduzenle_kullaniciadi.getText().toString());
                   preparedStatement.setString(2, profilduzenle_kullanicisifre.getText().toString());
                    preparedStatement.setString(3, profilduzenle_email.getText().toString());
                    preparedStatement.setString(4, profilduzenle_telefonno.getText().toString());
                    preparedStatement.setString(5,vtkcinsiyet.toString());
                   preparedStatement.setString(6, vtksehir.toString());
                    preparedStatement.setString(7, vtkmeslek.toString());
                    preparedStatement.setDate(8,sqlDate);






                    String kullaniciBilgileriSQL = "UPDATE kullanici_bilgileri SET "
                            + "adi = ?, "
                            + "soyadi = ?, "
                            + "adresi = ?, "
                            + "hakkinda = ? "
                            + "WHERE ID = "+ anahtar +";";
                    try (PreparedStatement preparedStatement4 = connection.prepareStatement(kullaniciBilgileriSQL)) {
                        preparedStatement4.setString(1, profilduzenle_ad.getText().toString());
                        preparedStatement4.setString(2, profilduzenle_soyad.getText().toString());
                        preparedStatement4.setString(3, profilduzenle_adres.getText().toString());


                        preparedStatement4.setString(4, profilduzenle_hakkinda.getText().toString());

                        preparedStatement4.executeUpdate();
                        preparedStatement.executeUpdate();

                        Toast.makeText(profilDuzenle.this,"Güncellendi",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(profilDuzenle.this,ProfilFragment.class);
                        startActivity(intent);

                    }




                }






                } catch (SQLException e) {
                e.printStackTrace();
            }
        }





            }


    void cinsiyet_vt() {

        PopupMenu popupMenu = new PopupMenu(profilDuzenle.this, cinsiyet_btn);

        popupMenu.getMenuInflater().inflate(R.menu.cinsiyet_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(profilDuzenle.this, "Seçilen" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                cinsiyet_btn.setText(menuItem.getTitle());
                vtkcinsiyet = menuItem.getTitle().toString();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    void sehir_vt() {

        PopupMenu popupMenu = new PopupMenu(profilDuzenle.this, sehir_btn);

        popupMenu.getMenuInflater().inflate(R.menu.sehir_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(profilDuzenle.this, "Seçilen" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                sehir_btn.setText(menuItem.getTitle());
                 vtksehir = menuItem.getTitle().toString();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    void dogumt_vt()
    {

        hangisi=1;

       final   Calendar c = Calendar.getInstance();


        // on below line we are getting
        // our day, month and year.
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH);
         int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                profilDuzenle.this,
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        dogumt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                       vt_dtarih = LocalDate.of(year,(monthOfYear+1),dayOfMonth);
                       sqlDate= java.sql.Date.valueOf(String.valueOf(vt_dtarih));

                       vtkdogumt = year+"-" + (monthOfYear+1) + "-" + dayOfMonth;

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

        PopupMenu popupMenu = new PopupMenu(profilDuzenle.this, meslek_btn);

        popupMenu.getMenuInflater().inflate(R.menu.meslek, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(profilDuzenle.this, "Seçilen" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                meslek_btn.setText(menuItem.getTitle());
                vtkmeslek = menuItem.getTitle().toString();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }
}
