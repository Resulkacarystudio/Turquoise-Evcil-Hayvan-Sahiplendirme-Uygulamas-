package com.resulkacar.turquoise;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class akisFragment extends Fragment {
    int ID;

    private ViewPager viewPager;
    private Connection connection = null;
    private List<PhotoData> photoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akis, container, false);
        viewPager = view.findViewById(R.id.viewPager);

        // Veritabanına bağlan
        baglan();

        // Veritabanından fotoğraf ve diğer verileri çek
        readDataFromDatabase();

        AkisPagerAdapter adapter = new AkisPagerAdapter();
        viewPager.setAdapter(adapter);

        return view;
    }

    public void baglan() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String ip = "192.168.170.75";
            String port = "1433";
            String database = "evcilhayvansahiplendirme";
            String username = "sa";
            String password = "1";
            String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;

            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void readDataFromDatabase() {
        baglan();
        try (Statement statement = connection.createStatement()) {
            // SQL sorgusunu oluştur ve kullanıcı gönderilerini çek
            String sql = "SELECT * FROM kullanici_gonderi " +
                    "JOIN kullanici_tablosu ON kullanici_gonderi.ID = kullanici_tablosu.ID " +
                    "JOIN kullanici_bilgileri ON kullanici_gonderi.ID = kullanici_bilgileri.ID";

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    byte[] gonderi = resultSet.getBytes("fotograf");
                    String ad = resultSet.getString("kullanici_adi");
                    String soyad = resultSet.getString("kullanici_email");
                    String sehir = resultSet.getString("kullanici_sehir");
                    ID = resultSet.getInt("ID");

                    if (gonderi != null) {
                        photoList.add(new PhotoData(gonderi, ad, soyad, sehir, null));

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private class AkisPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @SuppressLint("ResourceAsColor")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            FrameLayout layout = new FrameLayout(requireContext());
            layout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            // LinearLayout for the profile photo and text
            LinearLayout infoLayout = new LinearLayout(requireContext());
            FrameLayout.LayoutParams infoParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            infoParams.gravity = Gravity.START | Gravity.BOTTOM;  // Sol üst köşe
            infoLayout.setLayoutParams(infoParams);
            infoLayout.setOrientation(LinearLayout.HORIZONTAL);

            // ImageButton for the profile photo
            ImageButton profileImageButton = new ImageButton(requireContext());
            LinearLayout.LayoutParams profileParams = new LinearLayout.LayoutParams(
                    150, // Sabit bir genişlik (Ajust the size as needed)
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            profileParams.gravity = Gravity.START | Gravity.BOTTOM;  // Sol üst köşe
            profileImageButton.setLayoutParams(profileParams);

            // Load profile photo from database
            byte[] profilePhoto = photoList.get(position).getProfilResmi();
            if (profilePhoto != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(profilePhoto, 0, profilePhoto.length);
                profileImageButton.setImageBitmap(bitmap);
            }

            // ImageView for the post photo
            ImageView imageView = new ImageView(requireContext());
            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            imageView.setLayoutParams(imageParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(photoList.get(position).getPhoto(), 0, photoList.get(position).getPhoto().length));

            // TextView for name, surname, and city
            TextView infoTextView = new TextView(requireContext());
            LinearLayout.LayoutParams infoTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            infoTextView.setLayoutParams(infoTextParams);
            infoTextView.setText(photoList.get(position).getAd() +"\n" + photoList.get(position).getSehir());
            infoTextView.setTextSize(18);
            infoTextView.setTextColor(getResources().getColor(android.R.color.white));


            profileImageButton.setImageResource(R.drawable.turqu);
            profileImageButton.setBackgroundColor(R.color.white);
            profileImageButton.setAlpha(0.7f);
            infoTextView.setBackgroundColor(R.color.black);
            infoTextView.setAlpha(0.7f);


            infoLayout.addView(profileImageButton);
            infoLayout.addView(infoTextView);

            layout.addView(imageView);
            layout.addView(infoLayout);

            container.addView(layout);

            profileImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                }
            });

            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
