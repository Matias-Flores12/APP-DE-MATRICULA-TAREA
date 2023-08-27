package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResumenMatriculaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_matricula);

        // Obtener extras del intent
        Intent intent = getIntent();
        String nombreAlumno = intent.getStringExtra("nombreAlumno");
        String escuela = intent.getStringExtra("escuela");
        String carrera = intent.getStringExtra("carrera");
        boolean carnetBiblioteca = intent.getBooleanExtra("carnetBiblioteca", false);
        boolean carnetMedioPasaje = intent.getBooleanExtra("carnetMedioPasaje", false);
        int cuotas = intent.getIntExtra("cuotas", 0); // Obtener el número de cuotas
        double totalAPagar = intent.getDoubleExtra("totalAPagar", 0.0); // Obtener el total a pagar

        // Mostrar los datos en los elementos de la interfaz
        TextView textViewResumen = findViewById(R.id.textViewResumen);
        textViewResumen.setText("Resumen de Matrícula para " + nombreAlumno);

        TextView textViewEscuela = findViewById(R.id.textViewEscuela);
        textViewEscuela.setText("Escuela: " + escuela);

        TextView textViewCarrera = findViewById(R.id.textViewCarrera);
        textViewCarrera.setText("Carrera: " + carrera);

        TextView textViewCarnetBiblioteca = findViewById(R.id.textViewCarnetBiblioteca);
        textViewCarnetBiblioteca.setText("Carnet Biblioteca: " + (carnetBiblioteca ? "Sí" : "No"));

        TextView textViewCarnetMedioPasaje = findViewById(R.id.textViewCarnetMedioPasaje);
        textViewCarnetMedioPasaje.setText("Carnet Medio Pasaje: " + (carnetMedioPasaje ? "Sí" : "No"));

        // Mostrar el número de cuotas
        TextView textViewCuotas = findViewById(R.id.textViewCuotas);
        textViewCuotas.setText("Cuotas: " + cuotas);

        // Mostrar el total a pagar
        TextView textViewTotalPagar = findViewById(R.id.textViewTotalPagar);
        textViewTotalPagar.setText("Total a pagar: S/. " + String.format("%.2f", totalAPagar));

        // Agregar más elementos TextView para mostrar otros detalles si es necesario
    }
}
