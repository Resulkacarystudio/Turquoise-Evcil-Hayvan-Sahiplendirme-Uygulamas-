package com.resulkacar.turquoise;


import static com.resulkacar.turquoise.R.drawable.dene4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AramaFragment extends Fragment {

    private ListView messageListView;
    private ArrayAdapter<String> messageAdapter;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arama, container, false);

        messageListView = view.findViewById(R.id.messageListView);

        List<String> messages = getMessagesFromDatabase(); // Veritabanından mesajları al

        messageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, messages);
        messageListView.setAdapter(messageAdapter);




        return view;
    }

    private List<String> getMessagesFromDatabase() {
        List<String> messages = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String ip = "192.168.170.75";
        String port = "1433";
        String database = "evcilhayvansahiplendirme";
        String username = "sa";
        String password = "1";

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                // SQL sorgusu
                String sql = "SELECT * FROM kullanici_mesajlari";

                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {

                    while (resultSet.next()) {
                        String kullaniciMesaj = resultSet.getString("mesaj");
                        int okudumu = resultSet.getInt("okudunmu");
                        int id = resultSet.getInt("ID");

                        // Verileri kullan
                        String message = "ID: " + id + ", Mesaj: " + kullaniciMesaj + ", Okundu: " + okudumu;
                        messages.add(message);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return messages;
    }

}
