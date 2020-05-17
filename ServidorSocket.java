import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket{

    public static void main(String[] args) {
        ServerSocket servidor = null;
        try {
            System.out.println("Iniciando Servidor");
            servidor = new ServerSocket(9999);
            System.out.println("Servidor Socket iniciado");

            while(true){
                Socket usuario = servidor.accept();
                new GerenciaSocket(usuario);
            }

        } catch (IOException e) {
            
            try {
                if(servidor != null)
                servidor.close();
            } catch (IOException e1) {}

            System.err.println("Porta em uso");
            e.printStackTrace();
        }
    };
}