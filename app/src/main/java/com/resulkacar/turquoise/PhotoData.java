// PhotoData.java
package com.resulkacar.turquoise;

public class PhotoData {
    private byte[] photo;
    private String ad;
    private String soyad;
    private String sehir;
    private byte[] profilResmi;

    public PhotoData(byte[] photo, String ad, String soyad, String sehir, byte[] profilResmi) {
        this.photo = photo;
        this.ad = ad;
        this.soyad = soyad;
        this.sehir = sehir;
        this.profilResmi = profilResmi;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public String getSehir() {
        return sehir;
    }

    public byte[] getProfilResmi() {
        return profilResmi;
    }
}
