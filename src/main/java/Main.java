import dao.FuncionarioDAO;
import entity.Funcionario;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FuncionarioDAO funDAO = new FuncionarioDAO();
        Funcionario funcionario = funDAO.getFuncionario(7);
    }
}
