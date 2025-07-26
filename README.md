# 🐾 Evcil Hayvan Sahiplendirme Android Uygulaması

## 🎯 Proje Amacı

Bu proje, kullanıcıların evcil hayvan sahiplenme sürecini kolaylaştırmak amacıyla geliştirilmiş bir Android uygulamasıdır.  
Kullanıcılar uygulama üzerinden hesap oluşturarak profillerini oluşturabilir, profil bilgilerini düzenleyebilir, profil resmi ve gönderi fotoğrafları yükleyebilir.  
Böylece kendilerini ve evcil hayvan ilanlarını paylaşabilir, diğer kullanıcılarla etkileşim kurabilirler.  

Temel amaç, kullanıcı deneyimini kolaylaştırmak ve evcil hayvan sahiplenme sürecini dijital ortama taşımaktır.

---

## 🛠️ Kullanılan Teknolojiler

- 📱 **Android Studio (Java)**: Uygulamanın geliştirilmesi için tercih edilen resmi Android geliştirme ortamı ve Java dili.
- 🗄️ **SQL Server (Microsoft)**: Uygulama verilerinin saklandığı merkezi veritabanı sistemi.
- 🔌 **JDBC jTDS Sürücüsü**: Android uygulaması ile SQL Server arasında bağlantı kurmak için kullanılan açık kaynaklı JDBC sürücüsü.
- 💾 **SharedPreferences**: Kullanıcı oturumu ve bazı uygulama ayarlarının cihazda saklanması için kullanılır.
- 🖼️ **Bitmap & ByteArray**: Profil fotoğrafları ve gönderiler, veritabanına byte dizisi olarak kaydedilip geri çağrılırken bitmap formatında işlenir.
- 📋 **PopupMenu, DatePickerDialog**: Kullanıcı arayüzünde seçimler için menü ve tarih seçici bileşenleri.
- ⚠️ **StrictMode**: SQL bağlantılarını ana iş parçacığında gerçekleştirebilmek için kullanılan Android kısıtlamalarını geçici olarak devre dışı bırakır.
- 🔐 **Android İzin Sistemi**: Galeriye erişim için kullanıcıdan `READ_EXTERNAL_STORAGE` izni istenir ve kontrol edilir.

---

## 🚀 Projenin Genel Özellikleri

- 🔑 **Kullanıcı Girişi:**  
  Kullanıcılar SQL Server üzerinden doğrulanır ve başarılı girişte kullanıcı ID'si cihazda saklanır.

- 📝 **Profil Yönetimi:**  
  Kullanıcılar profil bilgilerini görüntüler, düzenler, cinsiyet, şehir, meslek gibi alanları kolayca seçebilir.

- 🖼️ **Profil Fotoğrafı:**  
  Galeriden fotoğraf seçerek profil resmi güncellenebilir.

- 📸 **Gönderi Paylaşımı:**  
  Kullanıcılar fotoğraf yükleyip gönderi olarak paylaşabilir; paylaşılan fotoğraflar profil sayfasında grid görünümünde gösterilir.

- 🔄 **Veri Senkronizasyonu:**  
  Tüm bilgiler gerçek zamanlı olarak SQL Server veritabanına kaydedilir ve güncellenir.
