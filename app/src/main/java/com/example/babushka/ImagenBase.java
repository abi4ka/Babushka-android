package com.example.babushka;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
public class ImagenBase {

    //Convierte una imagen en Base64 a Bitmap

        public static Bitmap base64ToBitmap(String base64String) {

            if (base64String == null || base64String.isEmpty()) {
                return null;
            }

            // Si la imagen viene con prefijo "data:image/png;base64,..."
            if (base64String.contains(",")) {
                base64String = base64String.split(",")[1];
            }

            // Decodificamos el Base64 a bytes
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

            // Convertimos los bytes a Bitmap
            return BitmapFactory.decodeByteArray(
                    decodedBytes,
                    0,
                    decodedBytes.length
            );
        }
}