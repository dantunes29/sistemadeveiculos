package br.com.danilo.carros.dao;

import br.com.danilo.carros.entidade.Carro;
import br.com.danilo.carros.entidade.Usuario;
import br.com.danilo.carros.banco.FabricaConexao;
import br.com.danilo.carros.erros.ErroSistema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CrudDAO<Usuario>{

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(entidade.getId() == null){
                ps = conexao.prepareStatement("INSERT INTO usuario (login,senha) VALUES (?,?)");
            } else {
                ps = conexao.prepareStatement("update usuario set login=?, senha=? where id=?");
                ps.setInt(3, entidade.getId());
            }
            ps.setString(1, entidade.getLogin());
            ps.setString(2, entidade.getSenha());
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }

    @Override
    public void deletar(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps  = conexao.prepareStatement("delete from usuario where id = ?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o carro!", ex);
        }
    }

    @Override
    public List<Usuario> buscar() throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from usuario");
            ResultSet resultSet = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while(resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setLogin(resultSet.getString("login"));
                usuario.setSenha(resultSet.getString("senha"));
                usuarios.add(usuario);
            }
            FabricaConexao.fecharConexao();
            return usuarios;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os carros!",ex);
        }
    }
    
}
