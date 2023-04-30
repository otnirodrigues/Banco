package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Ver anotações TODO no código
public class TransferirActivity extends AppCompatActivity {

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

        valorOperacao.setHint(valorOperacao.getHint() + " transferido");
        tipoOperacao.setText("TRANSFERIR");
        btnOperacao.setText("Transferir");

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    String numDestino = numeroContaDestino.getText().toString();

                    // Validando as entrada do usuário na opção de tranferência
                    if (numOrigem.isEmpty() || numDestino.isEmpty()) {
                        numeroContaOrigem.setError("Número da conta não pode ser vazio");
                        numeroContaDestino.setError("Número da conta não pode ser vazio");
                        return;
                    }else if (valorOperacao.getText().toString().isEmpty()) {
                        valorOperacao.setError("Valor não pode ser vazio");
                        return;
                    }else  if (numeroContaOrigem.equals(numeroContaDestino)) {
                        numeroContaDestino.setError("A transferência não pode ser realizada, os números das contas são iguais!");
                        return;
                    }else{
                        Toast.makeText(this, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                    // Executando a tranferência
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.transferir(numOrigem, numDestino, valor);
                    finish();
                }
        );

    }
}