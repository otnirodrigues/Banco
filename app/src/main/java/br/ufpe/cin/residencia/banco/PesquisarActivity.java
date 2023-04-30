package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

//Ver anotações TODO no código
public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString();
                    int radioButtonSelecionado = tipoPesquisa.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioButtonSelecionado);
                    String entrada = radioButton.getText().toString();

                    // Verificando se a pesquisa está sendo feita por nome.
                    if (entrada.equals("Nome")) {
                        // se a entrada for diferente de null, e == NOME, pesquisar
                        if (!oQueFoiDigitado.isEmpty()) {
                            viewModel.buscarPeloNome(oQueFoiDigitado);
                            Toast.makeText(this, "Busca realizada por nome.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (entrada.equals("CPF")) {
                        // se a entrada for diferente de null, e == CPF, pesquisar
                        if (!oQueFoiDigitado.isEmpty()) {
                            viewModel.buscarPeloCPF(oQueFoiDigitado);
                            Toast.makeText(this, "Busca realizada por CPF.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (!oQueFoiDigitado.isEmpty()) {
                            // se a entrada for diferente de null, e != NOME e CPF, pesquisar
                            viewModel.buscarPeloNumero(oQueFoiDigitado);
                            Toast.makeText(this, "Busca realizada por Número da Conta.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Listando as contas encontradas na pesquisa, caso encontre alguma conta.
        viewModel.listaContasAtual.observe(this, conta -> {
            if(conta.isEmpty()){
                Toast.makeText(this, "Nenhuma conta encontrada na pesquisa.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Contas: ", Toast.LENGTH_SHORT).show();
            }
            adapter.submitList(conta);
        });

    }
}