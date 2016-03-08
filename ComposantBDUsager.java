package biblio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Composant logiciel assurant la gestion des usagers.
 */
public class ComposantBDUsager {

	/**
	 * Récupération de la liste complète des usagers triée par nom.
	 * 
	 * @return un <code>ArrayList<String[]></code> contenant autant
	 *         de tableaux de String (avec id, nom, prenom, statut, email)
	 *         que d'usagers dans la base de données.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeTousUsagers() throws SQLException {
		
		ArrayList<String[]> users = new ArrayList<String[]>();

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from usager order by nom";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String[] user = new String[5];
			user[0] = rset.getString("id_abonne");
			user[1] = rset.getString("nom");
			user[2] = rset.getString("prenom");
			user[3] = rset.getString("statut");
			user[4] = rset.getString("email");

			users.add(user);
		}
		rset.close();
		stmt.close();

		return users;
	}

	/**
	 * Récupération de la liste complète des usagers ayant un nom donné.
	 * 
	 * @param nomUsager : nom de l'usager.
	 * @return un <code>ArrayList<String[]></code> contenant autant de tableaux de String (avec id, nom, prenom, statut, email) que d'employés
	 *         ayant le nom passé en paramètre
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeUsagers(String nomUsager) throws SQLException {
		ArrayList<String[]> users = new ArrayList<String[]>();

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from usager where nom='"+nomUsager+ "'" ;
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String[] user = new String[5];
			user[0] = rset.getString("id_abonne");
			user[1] = rset.getString("nom");
			user[2] = rset.getString("prenom");
			user[3] = rset.getString("statut");
			user[4] = rset.getString("email");

			users.add(user);
		}
		rset.close();
		stmt.close();

		return users;
		
	}

	/**
	 * Récupération des informations sur un usager à partir de son identifiant.
	 * 
	 * @param idUsager : id de l'usager à rechercher
	 * @return un tableau de <code>String</code> contenant l'id, le nom, le prénom, le statut et l'email de l'usager.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static String[] getUsager(String idUsager) throws SQLException {
		
		String[] usager = new String[5];

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from usager where id_abonne ='" + idUsager + "'";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			
			usager[0] = rset.getString("id_abonne");
			usager[1] = rset.getString("nom");
			usager[2] = rset.getString("prenom");
			usager[3] = rset.getString("statut");
			usager[4] = rset.getString("email");

		}
		rset.close();
		stmt.close();

		return usager;
		
	}

	/**
	 * Récupération de la liste de noms des usagers (ayant un nom DISTINCT) à partir du début du nom.
	 * 
	 * @param debutNomUsager : un <code>String</code> correspondand au début du nom de l'usager.
	 * @return Un <code>ArrayList<String></code> contenant autant de <String> que d'usager commençant par la chaine de caractère donnée.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String> debutUsager(String debutNomUsager) throws SQLException {
		
		ArrayList<String> usagers = new ArrayList<String>();

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select distinct nom from usager where nom like '" + debutNomUsager + "%'";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String usager = rset.getString("nom");

			 usagers.add(usager);
		}
		rset.close();
		stmt.close();

		return usagers;
	}

	/**
	 * Référencement d'un nouvel usager dans la base de données.
	 * 
	 * @param nom
	 * @param prenom
	 * @param statut (deux valeurs possibles "Etudiant" et "Enseignant")
	 * @param email
	 * @return l'identifiant de l'usager référencé.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static int creerUsager(String nom, String prenom, String statut, String email) throws SQLException {
		Statement stmt = Connexion.getConnection().createStatement();
		
		String insert = "insert into usager values(nextval('usager_id_abonne_seq'), '"+nom+"','"+prenom+"','"+statut+"','"+email+"')";
		stmt.executeUpdate(insert);
		
		String query = "select currval('usager_id_abonne_seq') as valeur_courante_id_abonne_usager";
		ResultSet rset = stmt.executeQuery(query);
		rset.next();
		int id = rset.getInt("valeur_courante_id_abonne_usager");
		rset.close();
		stmt.close();

		return id;
	}

	/**
	 * Modification des informations d'un usager donné (via son identifiant id) :
	 * les nouvelles valeurs (nom, prenom, etc.) écrasent les anciennes.
	 * 
	 * @param idUsager : identifiant de l'usager dont on veut modifier les informations.
	 * @param nom
	 * @param prenom
	 * @param statut (deux valeurs possibles "Etudiant" et "Enseignant")
	 * @param email
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void modifierUsager(String idUsager, String nom, String prenom, String statut, String email) throws SQLException {
		
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query = "update usager set id_abonne= '" +idUsager + "', nom= '" +  nom + "', prenom ='"+prenom+"', statut = '" + statut + "', email = '" + email + "' where id_abonne = '" + idUsager + "'";
		
		stmt.executeUpdate(query);

		stmt.close();
	}

	/**
	 * Suppression d'un usager.
	 * 
	 * @param idUsager : identifiant de l'utilisateur
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void supprimerUsager(String idUsager) throws SQLException { // efface pas si a des emprunts en cours
		
		ArrayList<String> usagers = new ArrayList<String>();
		
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query ="select id_usager from emprunt where ( (date_retour is null) and (id_usager='"+idUsager+"') )";
		
		ResultSet rset = stmt.executeQuery(query);
		
		while (rset.next()) {
			usagers.add(rset.getString("id_usager"));
		}
		
		if(usagers.size()!=0) throw new SQLException();
		
		rset.close();
		
		String delete = "delete from usager where id_abonne = '" + idUsager + "'";
		System.out.println("dans supprimer usager " + delete);
		
		stmt.executeUpdate(delete);
		stmt.close();
		
	}
}
