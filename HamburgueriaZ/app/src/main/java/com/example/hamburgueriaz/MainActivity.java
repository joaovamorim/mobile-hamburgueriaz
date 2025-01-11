package com.example.hamburgueriaz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int quantidade = 0;
    private TextView tvQuantityValue;
    private TextView tvOrderSummary;
    private EditText etName;
    private CheckBox cbBacon, cbCheese, cbOnionRings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuantityValue = findViewById(R.id.tvQuantityValue);
        tvOrderSummary = findViewById(R.id.tvOrderSummary);
        etName = findViewById(R.id.etName);
        cbBacon = findViewById(R.id.cbBacon);
        cbCheese = findViewById(R.id.cbCheese);
        cbOnionRings = findViewById(R.id.cbOnionRings);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnOrder = findViewById(R.id.btnOrder);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                somar();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtrair();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPedido();
            }
        });
    }

    private void somar() {
        quantidade++;
        atualizarQuantidade();
    }

    private void subtrair() {
        if (quantidade > 0) {
            quantidade--;
        }
        atualizarQuantidade();
    }

    private void atualizarQuantidade() {
        tvQuantityValue.setText(String.valueOf(quantidade));
    }

    private void enviarPedido() {
        String nome = etName.getText().toString();
        boolean temBacon = cbBacon.isChecked();
        boolean temQueijo = cbCheese.isChecked();
        boolean temOnionRings = cbOnionRings.isChecked();

        double precoTotal = calcularPrecoTotal(quantidade, temBacon, temQueijo, temOnionRings);

        String resumoPedido = "Nome do cliente: " + nome + "\n" +
                "Tem Bacon? " + (temBacon ? "Sim" : "Não") + "\n" +
                "Tem Queijo? " + (temQueijo ? "Sim" : "Não") + "\n" +
                "Tem Onion Rings? " + (temOnionRings ? "Sim" : "Não") + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Preço final: R$ " + String.format("%.2f", precoTotal);

        tvOrderSummary.setText(resumoPedido);

        enviarEmail(nome, resumoPedido);
    }

    private double calcularPrecoTotal(int quantidade, boolean temBacon, boolean temQueijo, boolean temOnionRings) {
        double precoBase = 20.0;
        double precoBacon = 2.0;
        double precoQueijo = 2.0;
        double precoOnionRings = 3.0;

        double precoAdicionais = 0.0;
        if (temBacon) precoAdicionais += precoBacon;
        if (temQueijo) precoAdicionais += precoQueijo;
        if (temOnionRings) precoAdicionais += precoOnionRings;

        return quantidade * (precoBase + precoAdicionais);
    }

    private void enviarEmail(String nome, String resumoPedido) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:siteclour22345@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
        intent.putExtra(Intent.EXTRA_TEXT, resumoPedido);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}