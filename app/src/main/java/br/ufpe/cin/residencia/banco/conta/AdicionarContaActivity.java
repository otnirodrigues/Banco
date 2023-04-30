package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    // Fazendo validações para verificar se os campos foram preenchidos corretamente,
                    // logo apos, utilizando o viewModel para inserir a conta, validado a nova conta.
                    if(nomeCliente.isEmpty() || nomeCliente.length() <= 4){
                        campoNome.setError("O campo nome é obrigatório, e deve conter no mínimo 5 caracteres!");
                        return;
                    }
                    if(cpfCliente.isEmpty() || cpfCliente.length() != 11){
                        campoCPF.setError("O campo CPF é obrigatório,e deve conter 11 caracteres numéricos");
                        return;
                    }
                    if(numeroConta.isEmpty()){
                        campoNumero.setError("O campo Número da Conta é obrigatório,e deve ser preenchido!");
                        return;
                    }
                    if(saldoConta.isEmpty()){
                        campoSaldo.setError("O campo Saldo é obrigatório,e deve ser preenchido!");
                        return;
                    }

                    Conta conta = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                    viewModel.inserir(conta);
                    finish();
                }
        );

    }
}