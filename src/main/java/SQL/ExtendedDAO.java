package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import simplejdbc.DAO;
import simplejdbc.DAOException;

/**
 *
 * @author Axel
 */
public class ExtendedDAO extends DAO{
    
    public ExtendedDAO(DataSource dataSource) {
        super(dataSource);
    }
    
    public List<DiscountEntity> existingDiscountCode() throws DAOException {
		List<DiscountEntity> result = new LinkedList<>();
		String sql = "SELECT * FROM DISCOUNT_CODE";
		try (	Connection connection = myDataSource.getConnection(); 
			Statement stmt = connection.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				String code = rs.getString("DISCOUNT_CODE");
                                Double rate = rs.getDouble("RATE");
                                result.add(new DiscountEntity(code,rate));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		return result;
	}
    
    public void insertDiscount(DiscountEntity discount) throws SQLException {

		// Une requête SQL paramétrée
		String sql = "INSERT INTO DISCOUNT_CODE VALUES(?, ?)";
		try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                        // Définir la valeur du paramètre
			stmt.setString(1, discount.getCode());
			stmt.setDouble(2, discount.getRate());

			stmt.executeUpdate();

		}
		
	}
    
    public void deleteDiscount(String code) throws DAOException{
        
        String sql = "DELETE FROM DISCOUNT_CODE WHERE DISCOUNT_CODE = ?";
		try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                        // Définir la valeur du paramètre
			stmt.setString(1, code);
                        stmt.executeUpdate();

		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
        
    }
}
