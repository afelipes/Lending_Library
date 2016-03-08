package biblio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Composant logiciel assurant la gestion des livres et des exemplaires
 * de livre.
 */
public class ComposantBDLivre {

	/**
	 * Récupération de la liste complète des livres triée par titre.
	 * 
	 * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que de livres dans la base.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeTousLivres() throws SQLException {

		ArrayList<String[]> livres = new ArrayList<String[]>();

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from livre order by titre";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String[] livre = new String[5];
			livre[0] = rset.getString("id");
			livre[1] = rset.getString("isbn10");
			livre[2] = rset.getString("isbn13");
			livre[3] = rset.getString("titre");
			livre[4] = rset.getString("auteur");

			livres.add(livre);
		}
		rset.close();
		stmt.close();

		return livres;
	}

	/**
	 * Récupération de la liste des livres ayant un titre donné.
	 * 
	 * @param titre : titre du livre à rechercher.
	 * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que de livres dans la base.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String[]> listeLivres(String titre) throws SQLException {
		
		
		ArrayList<String[]> livres = new ArrayList<String[]>();

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from livre where titre = '" + titre + "'";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String[] livre = new String[5];
			livre[0] = rset.getString("id");
			livre[1] = rset.getString("isbn10");
			livre[2] = rset.getString("isbn13");
			livre[3] = rset.getString("titre");
			livre[4] = rset.getString("auteur");

			livres.add(livre);
		}
		rset.close();
		stmt.close();

		return livres;
	}

	/**
	 * Modification des informations du livre donné (via son identifiant id) :
	 * les nouvelles valeurs (isbn10, isbn13, etc.) écrasent les anciennes.
	 * 
	 * @param idLivre : id du livre à modifier.
	 * @param isbn10 : nouvelle valeur d'isbn10.
	 * @param isbn13 : nouvelle valeur d'isbn13.
	 * @param titre : nouvelle valeur du titre.
	 * @param auteur : nouvel auteur.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void modifierLivre(String idLivre, String isbn10, String isbn13, String titre, String auteur) throws SQLException 
	{	
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query = "update livre set isbn10 = '" + isbn10 + "', isbn13 = '" +  isbn13 + "', titre ='"+titre+"', auteur = '" + auteur + "' where id = '" + idLivre + "'";
		
		stmt.executeUpdate(query);

		stmt.close();
	}

	/**
	 * Supprime un livre à partir de son n° d'identifiant.
	 * 
	 * @param idLivre : id du livre à supprimer.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void supprimerLivre(String idLivre) throws SQLException {
		
		Statement stmt = Connexion.getConnection().createStatement();
		
		String delete = "delete from livre where id = " + idLivre;
		
		System.out.println("------------------> " + delete);
		stmt.executeUpdate(delete);
		stmt.close();
	}

	/**
	 * Récupère les informations (titre, auteur, etc.) d'un livre à partir de son n° d'identifiant.
	 * 
	 * @param idLivre : id du livre dont on veut les informations.
	 * @return un tableau de <code>String</code> contenant les informations d'un livre. 
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static String[] getLivre(String idLivre) throws SQLException {
		
		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from livre where id = '" + idLivre + "'";
		ResultSet rset = stmt.executeQuery(query);
		
		String[] livre = new String[5];
		
		while (rset.next()) {
			
			livre[0] = rset.getString("id");
			livre[1] = rset.getString("isbn10");
			livre[2] = rset.getString("isbn13");
			livre[3] = rset.getString("titre");
			livre[4] = rset.getString("auteur");
		}
		rset.close();
		stmt.close();

		return livre;
	}
	

	/**
	 * Retourne le nombre d'exemplaire d'un livre à partir de son numéro d'identifiant.
	 * 
	 * @param idLivre : id du livre dont on veut connaître le nombre d'exemplaires.
	 * @return le nombre d'exemplaires
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static int nbExemplaires(String idLivre) throws SQLException {

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select count(id_exempl) as nombre_ex from exemplaire where id_livre = '"+idLivre+"'";
		
		ResultSet rset = stmt.executeQuery(query);
		
		int nb_ex=0;
		
		while (rset.next()) {
		nb_ex = rset.getInt("nombre_ex");
		}
			
		rset.close();
		stmt.close();
		return nb_ex;
	}

	/**
	 * Récupération de la liste des identifiants d'exemplaires d'un livre donné.
	 * 
	 * @param idLivre : identifiant du livre dont on veut la liste des exemplaires.
	 * @return un <code>ArrayList</code> contenant les n° d'identifiant des exemplaires
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<Integer> listeExemplaires(String idLivre) throws SQLException {

		ArrayList<Integer> exemplaires = new ArrayList<Integer>();

		Statement stmt = Connexion.getConnection().createStatement();
		
		String query = "select id_exempl from exemplaire where id_livre = '"+idLivre+"'";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			int id_ex = rset.getInt("id_exempl");
			exemplaires.add(id_ex);
		}
		rset.close();
		stmt.close();
		
		return exemplaires;
	}

	/**
	 * Ajout d'un exemplaire à un livre donné identifié par son id.
	 * 
	 * @param id identifiant du livre dont on veut ajouter un exemplaire.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void ajouterExemplaire(String id) throws SQLException {
		
		Statement stmt = Connexion.getConnection().createStatement(); //Obtenir conexion à la bas de données+interaction avec base de données
		
		String query = "insert into exemplaire values ( nextval('exemplaire_id_exempl_seq'), 'Disponible','" +id+ "')" ;
		
		stmt.executeUpdate(query);
		
		stmt.close();
		
	}

	/**
	 * Suppression d'un exemplaire donné identifié par son id.
	 * @param idExemplaire : identifiant du livre dont on veut supprimer un exemplaire.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static void supprimerExemplaire(String idExemplaire) throws SQLException {
		
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query1= "select etat from exemplaire where id_exempl='"+idExemplaire+"'";
		ResultSet rset = stmt.executeQuery(query1);
		while (rset.next()) {
			String etatExempl = rset.getString("etat");
			if(etatExempl.equals("Emprunté")) throw new SQLException(); 
		}
		rset.close();
		
		String query = "delete from exemplaire where id_exempl = '" + idExemplaire + "'";
		stmt.executeUpdate(query);
		stmt.close();

	}

	/**
	 * Détermine si un exemplaire est actuellement emprunté.
	 * 
	 * @param idExemplaire
	 * @return <code>true</code> si l'exemplaire est emprunté, <code>false</code> sinon
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static Boolean estEmprunte(int idExemplaire) throws SQLException {  //On pourra savoir si ca marche seulement après avoir écrit ComposantBDEmprunt
		
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query = "select id_exemplaire from emprunt where date_retour is null and id_exemplaire = '"+idExemplaire+"'";
		ResultSet rset = stmt.executeQuery(query);
		boolean reponse = false;
		
		while (rset.next()) {
			if (rset.getInt("id_exemplaire") == idExemplaire) {reponse = true; }
		}
		
		rset.close();
		stmt.close();
		
		return reponse;
	}

	/**
	 * Détermine si un exemplaire est actuellement emprunté.
	 * 
	 * @param idExemplaire : id de l'exemplaire.
	 * @return "true" si l'exemplaire est emprunté, "false" sinon.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static Boolean aEteEmprunte(int idExemplaire) throws SQLException {
		Statement stmt = Connexion.getConnection().createStatement();
		
		String query = "select id_exemplaire from emprunt where date_retour is not null and id_exemplaire = '"+idExemplaire+"'";
		ResultSet rset = stmt.executeQuery(query);
		boolean reponse = false;
		
		while (rset.next()) {
			if (rset.getInt("id_exemplaire") == idExemplaire) {reponse = true; }
			else {reponse = false; }
			}
		
		rset.close();
		stmt.close();
		return reponse;
	}

	/**
	 * Récupération de la liste de titre des livres à partir du début du titre.
	 * 
	 * @param debutTitreLivre : un <code>String</code> correspond au début du titre du livre
	 * @return Un <code>ArrayList<String></code> contenant autant de <String> que de livres potentiels.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static ArrayList<String> debutLivre(String debutTitreLivre) throws SQLException { // problème: apostrophe
		
		ArrayList<String> livres = new ArrayList<String>();

		Statement stmt = Connexion.getConnection().createStatement();
		String query = "select * from livre where titre like '" + debutTitreLivre + "%'";
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) {
			String livre = rset.getString("titre");

			livres.add(livre);
		}
		rset.close();
		stmt.close();

		return livres;
	}

	/**
	 * Référencement d'un nouveau livre dans la base de données.
	 * 
	 * @param isbn10
	 * @param isbn13
	 * @param titre
	 * @param auteur
	 * @return l'identifiant (id) du livre créé.
	 * @throws SQLException en cas d'erreur de connexion à la base.
	 */
	public static int creerLivre(String isbn10, String isbn13, String titre, String auteur) throws SQLException {
		Statement stmt = Connexion.getConnection().createStatement();
		
		String insert = "insert into livre values(nextval('livre_id_seq'), '"+isbn10+"','"+isbn13+"','"+titre+"','"+auteur+"')";
		stmt.executeUpdate(insert);
		
		String query = "select currval('livre_id_seq') as valeur_courante_id_livre";
		ResultSet rset = stmt.executeQuery(query);
		rset.next();
		int id = rset.getInt("valeur_courante_id_livre");
		rset.close();
		stmt.close();

		return id;
	}
}
