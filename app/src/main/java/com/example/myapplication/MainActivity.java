package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBoxCarnetBiblioteca;
    private CheckBox checkBoxCarnetMedioPasaje;
    private RadioGroup radioGroupCuotas;
    private TextView textViewTotalPagar;
    private Button buttonCalcularTotal;
    private Button buttonImprimirTotal;

    private Spinner spinnerEscuela;
    private Spinner spinnerCarrera;

    private static final double PENSION_BASE = 3000;
    private static final double PORCENTAJE_DESCUENTO = 0.12;
    private static final double GASTOS_ADICIONALES_BIBLIOTECA = 25;
    private static final double GASTOS_ADICIONALES_MEDIO_PASAJE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de variables
        checkBoxCarnetBiblioteca = findViewById(R.id.checkBoxCarnetBiblioteca);
        checkBoxCarnetMedioPasaje = findViewById(R.id.checkBoxCarnetMedioPasaje);
        radioGroupCuotas = findViewById(R.id.radioGroupCuotas);
        textViewTotalPagar = findViewById(R.id.textViewTotalPagar);
        buttonCalcularTotal = findViewById(R.id.buttonCalcularTotal);
        buttonImprimirTotal = findViewById(R.id.buttonImprimirTotal);

        spinnerEscuela = findViewById(R.id.spinnerEscuela);
        spinnerCarrera = findViewById(R.id.spinnerCarrera);

        // Crear adaptadores para los spinners
        ArrayAdapter<CharSequence> escuelaAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.escuelas_array,
                android.R.layout.simple_spinner_item
        );
        escuelaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEscuela.setAdapter(escuelaAdapter);

        ArrayAdapter<CharSequence> carreraAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.carreras_array,
                android.R.layout.simple_spinner_item
        );
        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera.setAdapter(carreraAdapter);

        // Configurar el botón para calcular el total
        buttonCalcularTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularTotal();
            }
        });

        // Configurar el botón para imprimir el total
        buttonImprimirTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imprimirTotal();
            }
        });

        // Mostrar los cálculos iniciales
        calcularTotal();
    }

    private void calcularTotal() {
        // Establecer valores predefinidos
        double costoCarrera = 3000.0; // Costo fijo para todas las carreras

        // Verificar si hay un RadioButton seleccionado
        RadioButton selectedRadioButton = findViewById(radioGroupCuotas.getCheckedRadioButtonId());
        if (selectedRadioButton == null) {
            // No hay RadioButton seleccionado, salir del método
            return;
        }

        int cuotas = Integer.parseInt(selectedRadioButton.getText().toString());

        // Calcular la pensión con el descuento
        double pensionConDescuento = PENSION_BASE - (PENSION_BASE * PORCENTAJE_DESCUENTO);

        // Calcular la pensión a pagar por cuota, considerando el cálculo correcto
        double totalPensionPorCuota = pensionConDescuento / cuotas;

        double gastosAdicionales = 0.0;

        if (checkBoxCarnetBiblioteca.isChecked()) {
            gastosAdicionales += GASTOS_ADICIONALES_BIBLIOTECA;
        }

        if (checkBoxCarnetMedioPasaje.isChecked()) {
            gastosAdicionales += GASTOS_ADICIONALES_MEDIO_PASAJE;
        }

        double totalAPagar = (pensionConDescuento + (costoCarrera * PORCENTAJE_DESCUENTO)) / cuotas + gastosAdicionales;

        textViewTotalPagar.setText("Total a pagar: S/. " + String.format("%.2f", totalAPagar));
    }

    private void imprimirTotal() {
        // Obtener los valores necesarios para imprimir
        String nombreAlumno = ((EditText) findViewById(R.id.editTextAlumnos)).getText().toString();
        String escuela = spinnerEscuela.getSelectedItem().toString();
        String carrera = spinnerCarrera.getSelectedItem().toString();
        boolean carnetBiblioteca = checkBoxCarnetBiblioteca.isChecked();
        boolean carnetMedioPasaje = checkBoxCarnetMedioPasaje.isChecked();

        // Calcular el total a pagar
        calcularTotal(); // Llamar al método para realizar los cálculos necesarios

        // Obtener el número de cuotas seleccionado
        RadioButton selectedRadioButton = findViewById(radioGroupCuotas.getCheckedRadioButtonId());
        int cuotas = Integer.parseInt(selectedRadioButton.getText().toString());

        // Obtener el total a pagar del TextView
        String totalAPagarText = textViewTotalPagar.getText().toString();
        // Extraer el valor numérico del texto (eliminar "Total a pagar: S/. " y convertir a double)
        double totalAPagar = Double.parseDouble(totalAPagarText.substring("Total a pagar: S/. ".length()));

        // Crear un intent para abrir la actividad de resumen
        Intent intent = new Intent(this, ResumenMatriculaActivity.class);

        // Pasar los datos como extras al intent
        intent.putExtra("nombreAlumno", nombreAlumno);
        intent.putExtra("escuela", escuela);
        intent.putExtra("carrera", carrera);
        intent.putExtra("carnetBiblioteca", carnetBiblioteca);
        intent.putExtra("carnetMedioPasaje", carnetMedioPasaje);
        intent.putExtra("cuotas", cuotas);
        intent.putExtra("totalAPagar", totalAPagar);

        // Iniciar la actividad de resumen
        startActivity(intent);
    }


}
