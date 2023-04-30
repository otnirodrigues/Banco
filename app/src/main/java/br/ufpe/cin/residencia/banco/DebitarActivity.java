package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Ver anotações TODO no código
public class DebitarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);

        valorOperacao.setHint(valorOperacao.getHint() + " debitado");
        tipoOperacao.setText("DEBITAR");
        btnOperacao.setText("Debitar");

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();

                    // Validando as entradas do usúario na opção de debitar valor em conta
                    if (numOrigem.isEmpty()) {
                        numeroContaOrigem.setError("Número da conta não pode ser vazio");
                        return;
                    }else if (valorOperacao.getText().toString().isEmpty()) {
                        valorOperacao.setError("Valor não pode ser vazio");
                        return;
                    }else{
                        Toast.makeText(this, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }

                    // Executando a função de debitar o valor
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.debitar(numOrigem, valor);
                    finish();
                }
        );
    }
}