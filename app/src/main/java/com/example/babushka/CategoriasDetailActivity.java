package com.example.babushka;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

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

        // Obtener Referencia a la vista raíz
        ConstraintLayout rootLayout = findViewById(R.id.rootLayout);


        // Mostrar el nombre (por ahora solo texto)
        if (categoriaId != null) {
            CategoriaTitle.setText(categoriaId);
        }

        if (categoriaId != null) {

            int colorRes;

            switch (categoriaId) {
                case "HEALTH":
                    colorRes = R.color.cat_health;
                    break;
                case "ENTRANTES":
                    colorRes = R.color.cat_entrantes;
                    break;
                case "CARNES":
                    colorRes = R.color.cat_carnes;
                    break;
                case "PASTA":
                    colorRes = R.color.cat_pasta;
                    break;
                case "MAR":
                    colorRes = R.color.cat_mar;
                    break;
                case "ENSALADAS":
                    colorRes = R.color.cat_ensaladas;
                    break;
                case "POSTRES":
                    colorRes = R.color.cat_postres;
                    break;
                case "VEGETARIANO":
                    colorRes = R.color.cat_vegetariano;
                    break;
                case "VEGANO":
                    colorRes = R.color.cat_vegano;
                    break;
                case "SIN GLUTEN":
                    colorRes = R.color.cat_sin_gluten;
                    break;
                default:
                    colorRes = android.R.color.white; // color por defecto
                    break;
            }

            // Aplicar color de fondo
            rootLayout.setBackgroundColor(
                    ContextCompat.getColor(this, colorRes)
            );
        }
    }
}
