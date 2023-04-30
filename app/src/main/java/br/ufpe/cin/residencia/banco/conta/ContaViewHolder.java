package br.ufpe.cin.residencia.banco.conta;

import static br.ufpe.cin.residencia.banco.conta.EditarContaActivity.KEY_NUMERO_CONTA;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContaViewHolder  extends RecyclerView.ViewHolder {
    TextView nomeCliente = null;
    TextView infoConta = null;
    ImageView icone = null;

    public ContaViewHolder(@NonNull View linha) {
        super(linha);
        this.nomeCliente = linha.findViewById(R.id.nomeCliente);
        this.infoConta = linha.findViewById(R.id.infoConta);
        this.icone = linha.findViewById(R.id.icone);
    }

    void bindTo(Conta c) {
        this.nomeCliente.setText(c.nomeCliente);
        this.infoConta.setText(c.numero + " | " + "Saldo atual: R$" + NumberFormat.getCurrencyInstance().format(c.saldo));

        // Verificando se o saldo é positivo ou negativo,
        // caso seja positivo, a imagem exibida é uma, sendo negativo, a imagem é alterada
        if(c.saldo <= 0) {
            this.icone.setImageResource(R.drawable.delete);
        }else {
            this.icone.setImageResource(R.drawable.ok);
        }
        this.addListener(c.numero);
    }

    public void addListener(String numeroConta) {
        this.itemView.setOnClickListener(
                v -> {
                    Context c = this.itemView.getContext();
                    Intent intent = new Intent(c, EditarContaActivity.class);

                    //Utilizando o putExtra do intente informar o numero da conta ao usuário
                    // na tela de recuperação de dados.
                    intent.putExtra(KEY_NUMERO_CONTA, numeroConta);
                    c.startActivity(intent);
                }
        );
    }
}
