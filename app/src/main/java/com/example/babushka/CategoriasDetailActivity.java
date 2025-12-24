package com.example.babushka;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CategoriasDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_detail);

        // Recibir la categoría desde CategoriasFragment
        String categoriaId = getIntent().getStringExtra(EXTRA_CATEGORY_ID);

        // Referencia al TextView
        TextView CategoriaTitle = findViewById(R.id.CategoriaTitle);

        // Mostrar el nombre (por ahora solo texto)
        if (categoriaId != null) {
            CategoriaTitle.setText(categoriaId);
        }
    }
}
