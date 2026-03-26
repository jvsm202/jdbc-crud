package entity;

public class Funcionario {
    private int id;
    private String nome;
    private String email;

    public Funcionario(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public Funcionario(){}


    public int getId() {
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getEmail(){
        return  email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
