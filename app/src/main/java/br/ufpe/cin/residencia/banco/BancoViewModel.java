package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;
    private MutableLiveData<String> __erroMsg = new MutableLiveData<>();
    public LiveData<String> erroMsg = __erroMsg;
    private MutableLiveData<Conta> __contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = __contaAtual;
    private MutableLiveData<List<Conta>> __listaContasAtual = new MutableLiveData<>();
    public LiveData<List<Conta>> listaContasAtual = __listaContasAtual;

    public LiveData<List<Conta>> contas;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
       // Atualizando a lista de contas, buscando no repository
        this.contas = repository.getContas();
    }

    // Implementando métodos de Tranferir, creditar e debitar
    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        new Thread( ()->{
            //buscando conta de origem e destino pelo número da conta
            Conta contaOrigem = repository.buscarPeloNumero(numeroContaOrigem);
            Conta contaDdestino = repository.buscarPeloNumero(numeroContaDestino);

            // Validando as contas encontradas
            if(contaOrigem == null || contaDdestino == null){
                __erroMsg.postValue("Conta Inexistente!");
            }else if(contaOrigem.saldo <= 0){
                __erroMsg.postValue("Saldo Insuficiente para Transferencia!");
                // Validando se o valor da operação é válido
            }else if(valor <= 0){
                __erroMsg.postValue("Valor de tranferencia Inválido!");
            }else{
                // Executando a tranferencia
                // debitando e creditando nas contas e atualizando
                contaDdestino.creditar(valor);
                repository.atualizar(contaDdestino);
                contaOrigem.debitar(valor);
                repository.atualizar(contaOrigem);
            }
        }).start();
    }

    void creditar(String numeroConta, double valor) {
        new  Thread( ()->{
            Conta conta = repository.buscarPeloNumero(numeroConta);

            if(conta == null){
                __erroMsg.postValue("Conta Inexistente!");
            }else if(valor <= 0){
                __erroMsg.postValue("Valor Inválido!");
            }else{
                conta.creditar(valor);
                repository.atualizar(conta);
            }
        }).start();
    }

    void debitar(String numeroConta, double valor) {
        new  Thread( ()->{
            Conta conta = repository.buscarPeloNumero(numeroConta);

            if(conta == null){
                __erroMsg.postValue("Conta Inexistente!");
            }else if(valor <= 0){
                __erroMsg.postValue("Valor Inválido!");
            }else{
                conta.debitar(valor);
                repository.atualizar(conta);
            }
        }).start();
    }

    // Implementação dos métodos que buscam contas, por nome, cpf ou número da conta
    void buscarPeloNome(String nomeCliente) {
        new Thread( () -> {
            List<Conta> listaContas = this.repository.buscarPeloNome(nomeCliente);
            __listaContasAtual.postValue(listaContas);
        }).start();
    }

    void buscarPeloCPF(String cpfCliente) {
        new Thread( () -> {
            List<Conta> listaContas = this.repository.buscarPeloCPF(cpfCliente);
            __listaContasAtual.postValue(listaContas);

        }).start();
    }

    void buscarPeloNumero(String numeroConta) {
        new Thread( () -> {
            Conta conta = repository.buscarPeloNumero(numeroConta);
            List<Conta> listConta = new ArrayList<>();
            listConta.add(conta);
            __listaContasAtual.postValue(listConta);
        } ).start();
    }

    // Metodo que busca todos os saldos sas contas e gera um valor para ser utilizado no MainActivity
    public double saldoTodasContasBanco() {
        double saldoTotal = 0;
        for (Conta conta : this.contas.getValue()) {
            saldoTotal += conta.saldo;
        }
        return saldoTotal;
    }
}
