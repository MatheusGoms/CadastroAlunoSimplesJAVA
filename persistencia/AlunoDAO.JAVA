package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Aluno;

public class AlunoDAO extends DAO {
    
    private Connection conn;

    public AlunoDAO() {
        try {
            conn = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problemas ao abrir a conexão");
        }
    }
    
    public void insereAluno(Aluno a) throws SQLException {
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement("insert into alunos values(null, ?, ?, ?, ?)");
            
            stmt.setString(1, a.getMatricula());
            stmt.setString(2, a.getNome());
            stmt.setDouble(3, a.getNota01());
            stmt.setDouble(4, a.getNota02());
            
            // 0 - false, 1 - true
            int flag = stmt.executeUpdate();
            
            if(flag == 0)
                throw new SQLException("Erro ao inserir no banco.");
            
        } finally { // executa sempre (mesmo que o ocorra erro)
            if(conn != null)
                conn.close();
            if(stmt != null)
                stmt.close();
        }   
    }
    
    public Aluno getAlunoMatricula(String matricula) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.prepareStatement("select * from alunos where matricula = ?");
            
            stmt.setString(1, matricula);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                return criaAluno(rs);
            }
            
            return new Aluno();
        } finally {
            if(conn != null)
                conn.close();
            if(stmt != null)
                stmt.close();
            if(rs != null)
                rs.close();
        }
    }
    
    public List<Aluno> getAlunos() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.prepareStatement("select * from alunos");
            
            rs = stmt.executeQuery();
            List<Aluno> listaAlunos = new ArrayList<>();
            while(rs.next()) {
                listaAlunos.add(criaAluno(rs));
            }
            
            return listaAlunos;
        } finally {
            if(conn != null)
                conn.close();
            if(stmt != null)
                stmt.close();
            if(rs != null)
                rs.close();
        }
    }
    
    public void atualizaAluno(Aluno a) throws SQLException {
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement("update alunos set matricula = ?, nome = ?, nota01 = ?, nota02 = ? where id = ?");
            stmt.setString(1, a.getMatricula());
            stmt.setString(2, a.getNome());
            stmt.setDouble(3, a.getNota01());
            stmt.setDouble(4, a.getNota02());
            stmt.setLong(5, a.getId());
            
            int flag = stmt.executeUpdate();
            
            if(flag == 0)
                throw new SQLException("Erro ao atualizar o aluno");
        } finally {
            if(conn != null)
                conn.close();
            if(stmt != null)
                stmt.close();
        }
    }
    
    public void excluiAluno(long id) throws SQLException {
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement("delete from alunos where id = ?");
            stmt.setLong(1, id);
            
            int flag = stmt.executeUpdate();
            
            if(flag == 0)
                throw new SQLException("Erro ao atualizar o aluno");
        } finally {
            if(conn != null)
                conn.close();
            if(stmt != null)
                stmt.close();
        }
    }
    
    private Aluno criaAluno(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getInt("id"));
        a.setMatricula(rs.getString("matricula"));
        a.setNome(rs.getString("nome"));
        a.setNota01(rs.getDouble("nota01"));
        a.setNota02(rs.getDouble("nota02"));
        
        return a;
    }
}
