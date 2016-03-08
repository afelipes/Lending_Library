package biblio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;;

/**
 * Composant logiciel assurant la gestion des emprunts d'exemplaires
 * de livre par les usagers.
 */
public class ComposantBDEmprunt {

	/**
	 * Récupération de la liste complète des emprunts en fonction de leur statut.
	 * 
	 * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeEmprunts(String statut) throws SQLException {
		//
		// METHODE NON UTILISEE : INUTILE DE COMPLETER
		//
		return new ArrayList<String[]>();
	}

	/**
	 * Récupération de la liste complète des emprunts en cours.
	 * 
	 * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeEmpruntsEnCours() throws SQLException {
		
		ArrayList<String[]> empruntsEnCours = new ArrayList<String[]>();
		
		
		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from emprunt where date_retour is null order by id_exemplaire";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String[] emprunt = new String[3];
			emprunt[0] = rset.getString("id_usager");
			emprunt[1] = rset.getString("id_exemplaire");
			emprunt[2] = rset.getString("date_empr");
			
			empruntsEnCours.add(emprunt);
		}
		rset.close();
		stmt.close();

		return empruntsEnCours;
	}

	/**
	 * Récupération de la liste complète des emprunts passés.
	 * 
	 * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeEmpruntsHistorique() throws SQLException {
		
		ArrayList<String[]> emprunts = new ArrayList<String[]>();
		
		
		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from emprunt where date_retour is not null order by id_exemplaire";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String[] emprunt = new String[4];
			emprunt[0] = rset.getString("id_usager");
			emprunt[1] = rset.getString("id_exemplaire");
			emprunt[2] = rset.getString("date_empr");
			emprunt[3] = rset.getString("date_retour");
			
			emprunts.add(emprunt);
		}
		rset.close();
		stmt.close();

		return emprunts;
	}

	/**
	 * Emprunter un exemplaire à partir du n° d'usager et du n° d'exemplaire.
	 * 
	 * @param idUsager : id de l'usager.
	 * @param idExemplaire id de l'exemplaire emprunté.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void emprunter(String idUsager, String idExemplaire) throws SQLException { // MIERDA
		
		ComposantBDLivre l = new ComposantBDLivre();
		int id = Integer.parseInt(idExemplaire);
		if( l.estEmprunte( id ) ) throw new SQLException();
		
		Statement stmt = Connexion.getConnection().createStatement();
		String query = "insert into emprunt values( nextval('emprunt_id_emprunt_seq'), '"+idExemplaire+"','"+idUsager+"',CURRENT_TIMESTAMP, NULL)";
		stmt.executeUpdate(query);
		
		stmt.close();		
	}

	/**
	 * Rendre un exemplaire à partir du n° d'exemplaire.
	 * 
	 * @param idExemplaire id de l'exemplaire à rendre.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void rendre(String idExemplaire) throws SQLException {
		
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query = "select date_retour,date_empr from emprunt where id_exemplaire= '"+idExemplaire+"'";
		String date_ret=null;
		String date_emp=null;
		
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			
			date_ret = rset.getString("date_retour");
			
			date_emp = rset.getString("date_empr");
		}
		if(date_ret!=null) throw new SQLException();
		
		query="update emprunt set date_retour= CURRENT_TIMESTAMP where date_empr='"+date_emp+"' and id_exemplaire= '"+idExemplaire+"'";
		
		stmt.executeUpdate(query);

		stmt.close();
		rset.close(); 
	}
}
