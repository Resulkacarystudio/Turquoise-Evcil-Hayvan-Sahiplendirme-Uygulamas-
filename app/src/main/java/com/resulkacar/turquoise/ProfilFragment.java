package com.resulkacar.turquoise;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProfilFragment extends Fragment {

    GridView gridView;
    TextView vtkullanici_adi, profil_txt;
    Button profilduzenlebtn, fotografyuklebtn;
    SharedPreferences sharedPreferences;
    ImageView imageView;
    Uri imageUri;
    Uri imageUri1;
    Bitmap bitmap;
    Bitmap bitmap1;
    TextView gonderisi_sayisi;
    int sayi;

    int hangisi = 0;
    List<byte[]> photoList = new ArrayList<>();


    int GALLERY_REQUEST_CODE_PROFIL = 0;
    int GALLERY_REQUEST_CODE_GONDERI = 1;


    // Örnek fotoğraf kaynakları (kendi fotoğraflarınızı eklemeyi unutmayın)


    static String TAG = "evcilhayvansahiplendirme";


    private static String ip = "192.168.170.75";
    private static String port = "1433";
    private static String Classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "evcilhayvansahiplendirme";
    private static String username = "sa";
    private static String password = "1";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    private Connection connection = null;

    int anahtar;

    public ProfilFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profil, container, false);


        gridView = view.findViewById(R.id.gridView);
        vtkullanici_adi = view.findViewById(R.id.vtkullanici_txt);
        profilduzenlebtn = view.findViewById(R.id.profilduzenlebtn);
        profil_txt = view.findViewById(R.id.profil_txt);
        imageView = view.findViewById(R.id.imageViewprofil);
        fotografyuklebtn = view.findViewById(R.id.fotografyuklebtn);
        gonderisi_sayisi = view.findViewById(R.id.gonderi_sayisi);




        SharedPreferences sharedPref = requireActivity().getSharedPreferences("com.resulkacar.turquoise", Context.MODE_PRIVATE);
        anahtar = sharedPref.getInt("vtID", 0);

      //  gonderileri_getir();

        int[] photos;
        gridView = view.findViewById(R.id.gridView);

        // Veritabanından çekilen fotoğrafların byte dizileri
        photos = new int[]{
                R.drawable.dene1
                // Diğer resim kaynakları buraya eklenebilir
        };

        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return photoList.size();
            }

            @Override
            public Object getItem(int position) {
                return photoList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView == null) {
                    // Yeni bir ImageView oluşturun
                    imageView = new ImageView(getActivity());
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300)); // ImageView boyutunu ayarlayın
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(15, 15, 5, 15);
                } else {
                    imageView = (ImageView) convertView;
                }

                // Fotoğrafı ImageView'e ayarlayın
                imageView.setImageResource(photos[position]);

                return imageView;
            }
        });

        // GridView elemanlarına tıklama dinleyicisi ekleyin
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Tıklanan fotoğrafın pozisyonunu kullanarak istediğiniz işlemleri yapabilirsiniz
                Toast.makeText(getActivity(), "Tıklanan pozisyon: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        baglan();
        bilgiCek();
        gonderiler();

        gonderisi_sayisi.setText(sayi+"\nGönderi");
        profilduzenlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), profilDuzenle.class);
                startActivity(intent);


            }
        });

       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hangisi=0;
                profilPhotos();


            }
        });


        fotografyuklebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hangisi=1;
                profilPhotos1();
            }
        });

        return view;


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

    public void bilgiCek() {
        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(" SELECT *  FROM kullanici_tablosu JOIN kullanici_bilgileri ON kullanici_tablosu.ID = kullanici_bilgileri.ID where kullanici_tablosu.ID=" + anahtar + ";");

                if (resultSet.next()) {
                    String vtkadi = resultSet.getString("kullanici_adi").trim();
                    String vtad = resultSet.getString("adi").trim();
                    String vtsoyad = resultSet.getString("soyadi").trim();
                    String vthakkinda = resultSet.getString("hakkinda").trim();
                    String vtkID = resultSet.getString("ID").trim();
                    byte[] profilkontrol= resultSet.getBytes("profil_resmi");
                    if(profilkontrol!=null)
                    {
                        byte[]  vtprofilresmi = resultSet.getBytes("profil_resmi");

                        Bitmap bitmapvtprofil = BitmapFactory.decodeByteArray(vtprofilresmi,0,vtprofilresmi.length);
                        imageView.setImageBitmap(bitmapvtprofil);

                    }
                    else
                    {

                    }

                    vtkullanici_adi.setText("@" + vtkadi);
                    profil_txt.setText(vtad + " " +vtsoyad +"\n"+vthakkinda);

                }

            } catch (SQLException e) {
                System.out.println("hata" + e);
                throw new RuntimeException(e);
            }
        }






















    }




    public void profilPhotos()
    {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);










        }
        else
        {
            Intent photos = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photos,2);

        }

    }
    public void profilPhotos1()
    {
        Intent photos = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photos,2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent photos = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent photos1 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(photos,2);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                if (requireContext() != null) {
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);

                    baglan();

                    // Hangisi'yi 0 olarak ayarla ve işleme gönder

                    if (hangisi == 0) {
                        // Profil resmini güncelleme işlemleri
                        setProfilResmi(bitmap);
                    } else if (hangisi == 1) {
                        // Kullanıcı gönderisi ekleme işlemleri
                        kaydetKullaniciGonderi(bitmap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setProfilResmi(Bitmap bitmap) {
        baglan();
        String sql = "UPDATE kullanici_bilgileri SET profil_resmi = ? WHERE ID =" + anahtar + ";";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Profil resmini byte dizisine çevir
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // ? işaretlerine değerleri yerleştir
            preparedStatement.setBytes(1, byteArray);

            // Sorguyu çalıştır
            preparedStatement.executeUpdate();

            // imageView güncellemesini ana thread üzerinde yap
            imageView.setImageBitmap(bitmap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void kaydetKullaniciGonderi(Bitmap bitmap) {
        try {
            baglan();
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO kullanici_gonderi(ID,fotograf) VALUES(?,?)");

            try {
                // Profil resmini byte dizisine çevir
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                byte[] byteArray1 = stream1.toByteArray();

                // ? işaretlerine değerleri yerleştir
                preparedStatement2.setInt(1, anahtar);
                preparedStatement2.setBytes(2, byteArray1);

                // Sorguyu çalıştır
                preparedStatement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }











    public void gonderiler() {
        baglan();
        List<byte[]> photoList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            // SQL sorgusunu oluştur ve kullanıcı gönderilerini çek
            String sql = "SELECT * FROM kullanici_gonderi WHERE ID =" + anahtar;

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    byte[] gonderi = resultSet.getBytes("fotograf");
                    if (gonderi != null) {
                        photoList.add(gonderi);
                    }
                }
            }

            gridView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {


                    sayi=photoList.size();
                    return photoList.size();
                }


                @Override
                public Object getItem(int position) {
                    return photoList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    ImageView imageView;
                    if (convertView == null) {
                        // Yeni bir ImageView oluşturun
                        imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300)); // ImageView boyutunu ayarlayın
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setPadding(15, 15, 5, 15);
                    } else {
                        imageView = (ImageView) convertView;
                    }

                    // Fotoğrafı ImageView'e ayarlayın
                    byte[] gonderi = (byte[]) getItem(position);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(gonderi, 0, gonderi.length);
                    imageView.setImageBitmap(bitmap);

                    return imageView;
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }















    }














