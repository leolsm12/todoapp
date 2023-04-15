
package controler;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.sql.Date;
import model.Task;
import util.ConnectionFactory;


public class TaskController {
    
    public void save(Task task) throws SQLException{
        String sql = "INSERT INTO task (idProject,"
                +"name,"
                +"description,"
                +"completed,"
                +"notes,"
                +"deadline,"
                +"createdAt,"
                +"updateAt) VALUES (?, ? , ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3,task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5,task.getNotes());
            statement.setDate(6,new Date(task.getDeadline(). getTime()));
            statement.setDate(7,new Date(task.getCreatedAt(). getTime()));
            statement.setDate(8,new Date(task.getUpdatedAt(). getTime()));
            statement.execute();
            
        }catch (Exception ex){
            throw new RuntimeException("Erro ao salvar a tarefa" 
                    + ex.getMessage(), ex);
            
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
            
        }
        
        
        
    }
    
    public void update(Task task){
        
        String sql = "UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "completed = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Estabelecendo conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setInt(1,task.getIdProject());
            statement.setString(2,task.getName());
            statement.setString(3,task.getDescription());
            statement.setString(4,task.getNotes());
           
            statement.setBoolean(5,task.isIsCompleted());
            statement.setDate(6,new Date(task.getDeadline().getTime()));
            statement.setDate(7,new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();
            
            
        }catch(SQLException ex) {
             throw new RuntimeException("Erro ao atualizar a tarefa" 
                     + ex.getMessage(),ex);
        }        
        
    }
    
    public void removeById(int taskId) throws SQLException{
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            //criação da conexão do banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a query 
            statement = connection.prepareStatement(sql);
            
            //setando  valores 
            statement.setInt(1, taskId );
            
            //Executando a query
            statement.execute();
            
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao deletar a tarefa" 
                    + ex.getMessage(),ex);
            
        }finally {  
            ConnectionFactory.closeConnection(connection, statement);
            
            if(statement != null){
                statement.close();
            
            }
        }
        
        }
    
    public List<Task> getAll(int idProject){
        
        String sql = "SELECT * FROM tasks WHERE idProjects = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Ista de tarefas que será devolvida quando a chamada do metodo acontecer 
        List<Task> tasks = new ArrayList<>();
        
        try{
            //criação da conexão do banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement (sql);
            
            //setando o valor que corresponde ao filtro de busca 
            statement.setInt(1, idProject);
            
            //valor retornado pela execução da query 
            resultSet = statement.executeQuery();
            
            
            //Enquanto ouverem valores a serem percorridos no meu resultSet
            while(resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));      
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));                
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                tasks.add(task);
                
            
            }
            
        }catch(SQLException ex ){
            throw new RuntimeException("Erro ao inserir a tarefa"
                    + ex.getMessage(),ex);
            
        }finally {  
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //Lista de tarefas que foi criada e carregada do banco de dados
        return tasks;
    }
    
}
