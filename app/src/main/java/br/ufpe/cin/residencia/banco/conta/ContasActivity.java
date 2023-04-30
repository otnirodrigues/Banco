package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContasActivity extends AppCompatActivity {
    ContaAdapter adapter;
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);

        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.rvContas);

        adapter = new ContaAdapter(getLayoutInflater());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button adicionarConta = findViewById(R.id.btn_Adiciona);
        adicionarConta.setOnClickListener(
                v -> startActivity(new Intent(this, AdicionarContaActivity.class))
        );

        // Implementando a recuperação dos dados das contas no banco de dados.
        // Utilizando o submitList para recuperar de forma rápida e precisa esses dados.
        viewModel.contas.observe(this, contas -> {
            adapter.submitList(contas);
        });
    }
}