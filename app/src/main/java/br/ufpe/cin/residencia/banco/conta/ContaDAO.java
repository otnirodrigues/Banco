package br.ufpe.cin.residencia.banco.conta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Ver anotações TODO no código
@Dao
public interface ContaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void adicionar(Conta c);

    @Update
    void atualizarConta(Conta c);

    @Delete
    void deletarConta(Conta c);

    @Query("SELECT * FROM contas ORDER BY numero ASC")
    LiveData<List<Conta>> contas();

    // Criando metodos que busque contas por, nome, numero da conta e cpf do cliente
    @Query("SELECT * FROM contas WHERE numero = :numeroConta LIMIT 1")
    Conta buscarContaPorNumero(String numeroConta);

    @Query("SELECT * FROM contas WHERE nomeCliente LIKE :titularConta")
    List<Conta> buscarContaPorNome(String titularConta);

    @Query("SELECT * FROM contas WHERE cpfCliente LIKE :cpfTitularConta")
    List<Conta> buscarContaPorCPF(String cpfTitularConta);
}
