import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GerenciaSocket extends Thread {

    private Socket usuario;
    private String nomeCliente;
    private BufferedReader leitor;
    private PrintWriter escritor;
    private static final  Map<String,GerenciaSocket> usuarios = new HashMap<String, GerenciaSocket>();

    public GerenciaSocket(Socket usuario){
        this.usuario = usuario;
        start();

    }



    @Override
    public void run() {
        try{
            leitor = new BufferedReader(new InputStreamReader(usuario.getInputStream()));
            escritor = new PrintWriter(usuario.getOutputStream(), true);
            escritor.println("Digite seu nome");
            String msg = leitor.readLine();
            this.nomeCliente = msg.toLowerCase().replaceAll(",", "");
            escritor.println("Seja Bem Vindo " + this.nomeCliente);
            usuarios.put(this.nomeCliente, this);

            while(true){
                msg = leitor.readLine();
                if(msg.equals("/sair")){
                    this.usuario.close();
                }else if(msg.toLowerCase().startsWith("/privado ")){
                    String userDestino = msg.substring(9, msg.length());
                    System.out.println("Você está conversando com " + userDestino);
                    GerenciaSocket destinatario = usuarios.get(userDestino);
                    if(destinatario == null){
                        System.out.println("Usuario não existe");
                    }else{
                        escritor.println("Digite mensagem para " +destinatario.getNomeCliente());
                        destinatario.getEscritor().println(this.nomeCliente + ": " + leitor.readLine());
                    }
                //Lista usuarios logados
                }else if(msg.equals("/listar")){
                    StringBuffer str = new StringBuffer();
                    for(String c: usuarios.keySet()){
                        str.append(c);
                        str.append(",");
                    }
                    str.delete(str.length()-1, str.length());
                    escritor.println(str.toString());
                }
                else{   
                    escritor.println( this.nomeCliente +" disse: " + msg);
                }
            }
            
        } catch (IOException e){
            System.out.println("Usuario desconectado ");
            e.printStackTrace();
        }
    }

    /**
     * @return the escritor
     */
    public PrintWriter getEscritor() {
        return escritor;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }
}

