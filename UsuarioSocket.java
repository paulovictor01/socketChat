import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class UsuarioSocket {
    public static void main(String[] args) {
        try {
            final Socket usuario = new Socket("127.0.0.1", 9999);

            // Ler Mensagem
            new Thread(){
                @Override
                public void run(){
                    try {
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(usuario.getInputStream()));
                        
                        while(true){
                            String mensagem = leitor.readLine();
                            if(mensagem == null || mensagem.isEmpty())
                                continue;
                            System.out.println(mensagem);
                        }

                    } catch (IOException e) {
                        System.out.println("Erro ao ler a mensagem do servidor");
                        e.printStackTrace();
                    }
                }git
            }.start();

            // Escrever Mensagem
            PrintWriter escritor = new PrintWriter(usuario.getOutputStream(), true);
            BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));
            String msgt = "";
            while(true){
                msgt = terminal.readLine();
                if(msgt == null || msgt.length() == 0){
                    continue;
                }
                escritor.println(msgt);
                if(msgt.equalsIgnoreCase("/sair")){
                    System.exit(0);
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("Endereço invalido");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Porta invalida ou fora do ar");
            e.printStackTrace();
        }
    }
}