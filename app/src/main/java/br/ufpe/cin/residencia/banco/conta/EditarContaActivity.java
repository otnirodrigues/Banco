package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
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
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);
        viewModel.buscarPeloNumero(numeroConta);

        viewModel.contaAtual.observe( this, conta -> {
            campoNome.setText(conta.nomeCliente);
            campoNumero.setText(conta.numero);
            campoCPF.setText(conta.cpfCliente);
            campoSaldo.setText(String.valueOf(conta.saldo));
        });

        btnAtualizar.setText("Editar");
        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    // Validando os campos de entrada do usuário
                    if(nomeCliente.isEmpty()){
                        campoNome.setError("O campo nome é obrigatório, e deve conter no mínimo 5 caracteres!");
                        return;
                    }
                    if(cpfCliente.length() != 11){
                        campoCPF.setError("O campo CPF é obrigatório,e deve conter 11 caracteres numéricos");
                        return;
                    }
                    if(numeroConta.isEmpty()) {
                        campoNumero.setError("O campo Número da Conta é obrigatório,e deve ser preenchido!");
                    }
                    if(saldoConta.isEmpty()){
                        campoSaldo.setError("O campo Saldo é obrigatório,e deve ser preenchido!");
                        return;
                    }
                    try {
                        Double.parseDouble(saldoConta);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Saldo inválido", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Caso a conta a ser atualizada for válida, execultar a atualização
                    if (viewModel.contaAtual != null) {
                        Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente,cpfCliente);
                        viewModel.atualizar(c);
                        finish();
                        Toast.makeText(this, "Conta atualizada com sucessso!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Operação Inválida!", Toast.LENGTH_SHORT).show();
                    }

                }
        );

        // Implementando o método que remove uma conta
        btnRemover.setOnClickListener(v -> {
            //TODO implementar remoção da conta
            Conta c = viewModel.contaAtual.getValue();
            viewModel.remover(c);
            finish();
            Toast.makeText(this, "Conta removida com sucesso!", Toast.LENGTH_SHORT).show();

        });
    }
}
