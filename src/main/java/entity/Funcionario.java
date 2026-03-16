package entity;

public class Funcionario {
    private int id = 0;
    private String nome = null;
    private String email = null;

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
