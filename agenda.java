import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

class contato {
    String nome;
    int telefone;
    String endereco;
    String relacao;
    contato(String n, int t, String end, String rel){
        nome = n;
        telefone = t;
        endereco = end;
        relacao = rel;
    }
    public String toString(){
        return String.format("Nome: %s, Tel: %d, Endereco: %s, Relacao: %s", nome, telefone, endereco, relacao);
    }
    public String toFileString() {
        return String.format("%s;%d;%s;%s", nome, telefone, endereco, relacao);
    }

    public static contato fromFileString(String info) {
        String[] partes = info.split(";");
        return new contato(partes[0], Integer.parseInt(partes[1]), partes[2], partes[3]);
    }
}
class agendatel{
    int max = 1000;
    int tam = 0;
    int ind = 0;
    contato[] pessoas = new contato[max];
    contato buscar(String procu){
        for (int i = 0; i < tam; i++){
            if(pessoas[i].nome.toLowerCase().contains(procu.toLowerCase())){
                return pessoas[i];
            }
        }
        return null;
    }
    void add(String nome, int num, String end, String rel){
        for (int i = 0; i < tam; i++){
            if(pessoas[i] != null && pessoas[i].nome.toLowerCase().contains(nome.toLowerCase())){
                altera(nome, nome, num, end, rel);
                return;
            }
        }
        pessoas[tam] = new contato(nome, num, end, rel);
        tam++;
        }
    void altera(String nomeold, String nomenew, int num, String end, String rel){
        for (int i = 0; i < tam; i++){
            if(pessoas[i] != null && pessoas[i].nome.toLowerCase().contains(nomeold.toLowerCase())){
                pessoas[i].nome = nomenew;
                pessoas[i].telefone = num;
                pessoas[i].endereco = end;
                pessoas[i].relacao = rel;
                return;
            }
        }
    }
    void remove(String nome){
        for (int i = 0; i < tam; i++){
            if(pessoas[i] != null && pessoas[i].nome.toLowerCase().contains(nome.toLowerCase())){
                for(int j = i; j < tam-1; j++){
                    pessoas[i] = pessoas[i+1];
                }
                pessoas[tam - 1] = null;
                tam--;
                break;
            }
        }
    }
    void imprime(){
        for (int i = 0; i < tam; i++){
            System.out.println(pessoas[i].toString());
         }
    }
    void salvar(String nomearq){
        try {
            File arq = new File(nomearq);
            arq.createNewFile();
            FileWriter writer = new FileWriter(arq);
            for(int i = 0; i < tam; i++){
                writer.write(pessoas[i].toFileString()+"\n");
            }
            writer.close();
        }catch(Exception ex){
            ex.printStackTrace(System.out);
        }        
    }
    void carregar(String nomearq){
        try{
            File arq = new File(nomearq);
            Scanner scanner = new Scanner(arq);
            while(scanner.hasNextLine()){
                String str = scanner.nextLine();
                pessoas[tam] = contato.fromFileString(str);
                tam++;
            }
            scanner.close();
        }catch(Exception ex){
            ex.printStackTrace(System.out);
        }
    }
}
class Principal{
    public static void main(String[] args){
        agendatel agenda = new agendatel();
        agenda.add("Fulano", 99999999, "Rua A", "UFF");
        agenda.add("Ciclano", 88888888, "Rua B", "Cederj");
        agenda.add("Beltrano", 88889999, "Rua C", "Infancia");
        agenda.add("Fulano", 77777777, "Rua D", "UFF");
        agenda.remove("Ciclano");
        agenda.imprime();
        //agenda.salvar("asd.txt");
    }
}    
