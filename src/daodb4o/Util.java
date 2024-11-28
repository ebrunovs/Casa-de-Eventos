/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package daodb4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class Util {
	private static ObjectContainer manager=null;

	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;		//ja tem uma conexao
		
		//---------------------------------------
		//configurar e conectar/criar banco local
		//---------------------------------------

		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...

		config.common().objectClass(Cliente.class).cascadeOnDelete(false);
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
		config.common().objectClass(Cliente.class).cascadeOnActivate(true);
		config.common().objectClass(Senha.class).cascadeOnDelete(false);
		config.common().objectClass(Senha.class).cascadeOnUpdate(true);
		config.common().objectClass(Senha.class).cascadeOnActivate(true);
		config.common().objectClass(Evento.class).cascadeOnDelete(false);
		config.common().objectClass(Evento.class).cascadeOnUpdate(true);
		config.common().objectClass(Evento.class).cascadeOnActivate(true);
		
		//conexao local (embarcada)
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}


	public static ObjectContainer conectarBancoRemoto(){	
		
		if (manager != null)
			return manager;		//ja tem uma conexao
		//---------------------------------------
		//configurar e conectar/criar banco remoto
		//---------------------------------------

		ClientConfiguration config = Db4oClientServer.newClientConfiguration( ) ;
		config.common().messageLevel(0);   //0,1,2,3,4

		config.common().objectClass(Cliente.class).cascadeOnDelete(false);
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
		config.common().objectClass(Cliente.class).cascadeOnActivate(true);
		config.common().objectClass(Senha.class).cascadeOnDelete(false);
		config.common().objectClass(Senha.class).cascadeOnUpdate(true);
		config.common().objectClass(Senha.class).cascadeOnActivate(true);
		config.common().objectClass(Evento.class).cascadeOnDelete(false);
		config.common().objectClass(Evento.class).cascadeOnUpdate(true);
		config.common().objectClass(Evento.class).cascadeOnActivate(true);

		//Conex�o remota 
		//***************
		//String ipservidor="localhost";
		//String ipservidor="10.0.4.43";			// computador do professor (lab)
		String ipservidor = "54.163.92.174";		// AWS
		manager = Db4oClientServer.openClient(config, ipservidor, 34000,"usuario1","senha1");
		return manager;
	}
	
	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}

}
