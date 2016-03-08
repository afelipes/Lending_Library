package biblio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestion de la connexion à la base de données PostgreSQL.
 */
public class Connexion {

	  // Les paramètres de connexion à la base de données
	  private static String username = "projet_bibliotheque_role";
	  private static String password = "biblio";
	  private static String host     = "localhost";
	  private static String numPort  = "54321";
	  private static String base     = "PROJET_BIBLIOTHEQUE_BD";
	  
	  // L'instance de la connexion : elle reste active durant toute la durée de vie de l'application.
	  public static Connection connexion;
	  
	  // Etablissement de la connexion
	  public Connexion() {
		  initConnection();	 
	  }
		  
	  /**
	   * Crée une connexion à la base de données (PostgreSQL). Les paramètres de
	   * connexion à la base sont mémorisés dans les attributs de la classe (username,
	   * password, etc.).)
	   */
	  private static void initConnection() {
		  try
		    {
		      // Enregistrement dans le DriverManager de l'instance du driver JDBC de PostgreSQL
		      // Note : cette classe (OracleDriver) fait partie du driver JDBC d'Oracle
		      // contenu dans le fichier tpwebbd/WEB-INF/lib/classes12.jar
		      DriverManager.registerDriver(new org.postgresql.Driver());

		      // Construction de la chaîne de connexion à partir des infos
		      String connectString = "jdbc:postgresql://" + host + ":" + numPort + "/" + base;

		      // Instantiation de la connexion
		      connexion = DriverManager.getConnection(connectString,username,password);

		      // Message OK
		      System.out.println("Connexion a " + base + " sur " + host + " --> OK");
		    }
		    catch (SQLException e)
		    {
		      // Si erreur...
		      System.err.println("Connexion a " + base + " sur " + host + " impossibe !");
		      System.err.println("Erreur : " + e.getMessage());
		      //e.printStackTrace();
		    }
	  }
	  
	  /**
	   * Initialise une connexion ou renvoie la connexion existante.
	   * @return l'instance de <code>Connection</code> permettant de réaliser des
	   *         opérations sur la base de données.
	   */
	  public static Connection getConnection() {
		  if (connexion==null) {
			  initConnection();
		  }
		  return connexion;
	  }
	    

}
