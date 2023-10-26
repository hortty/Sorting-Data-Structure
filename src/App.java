import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        int[] tamanhosVet = {50, 500, 1000, 5000, 10000};
        Random gerador = new Random();
        int[] vetor = new int[10000];
        ResultadoExecucao[] resultadoBubble = new ResultadoExecucao[5];
        ResultadoExecucao[] resultadoShell = new ResultadoExecucao[5];
        ResultadoExecucao[] resultadoHeap = new ResultadoExecucao[5];

        ResultadoExecucao mediaBubble = new ResultadoExecucao(0, 0, 0);
        ResultadoExecucao mediaShell = new ResultadoExecucao(0,0,0);
        ResultadoExecucao mediaHeap = new ResultadoExecucao(0,0,0);

        for(int i=0;i<5;i++)
        {
            for(int j=0;j<tamanhosVet[i];j++)
            {
                vetor[j] = gerador.nextInt(10000);
            }

            for(int j=0;j<5;j++)
            {
                resultadoBubble[j] = BubbleSort(vetor, tamanhosVet[i]);
                resultadoShell[j] = ShellSort(vetor, tamanhosVet[i]);
                resultadoHeap[j] = HeapSort(vetor, tamanhosVet[i]);

            }

            System.out.println("\nTeste " + (i+1)); 
            PrintarMedia(CalcularMedia(resultadoBubble), "BubbleSort");
            PrintarMedia(CalcularMedia(resultadoShell), "ShellSort");
            PrintarMedia(CalcularMedia(resultadoHeap), "HeapSort");

        }
    }

    private static ResultadoExecucao CalcularMedia(ResultadoExecucao[] resultadoExecucao)
    {
        ResultadoExecucao media = new ResultadoExecucao(0,0,0);
        for(int i=0;i<5;i++)
        {
            media.NumIteracoes = media.NumIteracoes + resultadoExecucao[i].NumIteracoes;
            media.NumTrocas = media.NumTrocas + resultadoExecucao[i].NumTrocas;
            media.TempExecucao = media.TempExecucao + resultadoExecucao[i].TempExecucao;
        }

        media.NumIteracoes = media.NumIteracoes / 5;
        media.NumTrocas = media.NumTrocas / 5;
        media.TempExecucao = media.TempExecucao / 5;

        return media;
    }

    private static void PrintarMedia(ResultadoExecucao resultadoExecucao, String nomeSort)
    {
        System.out.println("--- " + nomeSort + " ---\nNumero de iteracoes: " + resultadoExecucao.NumIteracoes
                            + " / Numero de trocas: " + resultadoExecucao.NumTrocas
                                + " / Tempo de execucao: " + resultadoExecucao.TempExecucao + " ns ");  
        System.out.println("\n\n");            
    }

    static void Printar(int vetor[]) {
        int n = vetor.length;
        for (int i = 0; i < 50; i++) {
            System.out.print(vetor[i] + " ");
        }
        System.out.println();
    }

    public static ResultadoExecucao BubbleSort(int[] vetores, int tamanho)
    {
        long TempExecucao = 0;
        int NumTrocas = 0;
        int NumIteracoes = 0;

        long startTime = System.nanoTime();

        for(int i=0;i<tamanho;i++)
        {
            for(int j=0;j<tamanho-1;j++)
            {
                if(vetores[j] > vetores[j + 1])
                {
                    int aux = vetores[j];
                    vetores[j] = vetores[j+1];
                    vetores[j+1] = aux;
                    NumTrocas++;
                }
                NumIteracoes++;
            }
        }

        long endTime = System.nanoTime();
        TempExecucao = endTime - startTime;

        if(tamanho<60)
        {
            Printar(vetores);
        }

        ResultadoExecucao resultadoExecucao = new ResultadoExecucao(TempExecucao, NumTrocas, NumIteracoes);

        return resultadoExecucao;
    }

    public static ResultadoExecucao ShellSort(int[] vetores, int tamanho)
    {
        long TempExecucao = 0;
        int NumTrocas = 0;
        int NumIteracoes = 0;

        int diferencaGap = tamanho / 2; 
        
        long startTime = System.nanoTime();

        while (diferencaGap > 0) {
            for (int i = diferencaGap; i < tamanho; i++) {
                int temp = vetores[i];
                int j;
                for (j = i; j >= diferencaGap && vetores[j - diferencaGap] > temp; j -= diferencaGap) {
                    vetores[j] = vetores[j - diferencaGap];
                }
                vetores[j] = temp;
                NumTrocas++;
                NumIteracoes++;
            }
            diferencaGap = diferencaGap / 2; 
        }

        long endTime = System.nanoTime();
        TempExecucao = endTime - startTime;

        if(tamanho<60)
        {
            Printar(vetores);
        }

        return new ResultadoExecucao(TempExecucao, NumTrocas, NumIteracoes);
    }
    
    public static ResultadoExecucao HeapSort(int vetor[], int tamanho) {
        ResultadoExecucao resultadoExecucao = new ResultadoExecucao();

        long startTime = System.nanoTime();

        for (int i = tamanho / 2 - 1; i >= 0; i--) {
            OrdenarVetor(vetor, tamanho, i, resultadoExecucao);
        }

        for (int i = tamanho - 1; i >= 0; i--) {
            int temp = vetor[0];
            vetor[0] = vetor[i];
            vetor[i] = temp;
            resultadoExecucao.NumTrocas++;

            OrdenarVetor(vetor, i, 0, resultadoExecucao);
        }

        long endTime = System.nanoTime();
        resultadoExecucao.TempExecucao = endTime - startTime;

        if(tamanho<60)
        {
            Printar(vetor);
        }

        return resultadoExecucao;
    }
 
    static int OrdenarVetor(int vetor[], int n, int i, ResultadoExecucao resultadoExecucao) {

        resultadoExecucao.NumIteracoes++;

        int maiorValor = i; 
        int esquerdaValor = 2 * i + 1; 
        int direitaValor = 2 * i + 2; 

        if (esquerdaValor < n && vetor[esquerdaValor] > vetor[maiorValor]) {
            maiorValor = esquerdaValor;
        }

        if (direitaValor < n && vetor[direitaValor] > vetor[maiorValor]) {
            maiorValor = direitaValor;
        }

        if (maiorValor != i) {
            int temp = vetor[i];
            vetor[i] = vetor[maiorValor];
            vetor[maiorValor] = temp;

            resultadoExecucao.NumTrocas++;

            OrdenarVetor(vetor, n, maiorValor, resultadoExecucao);
        }

        return resultadoExecucao.NumTrocas;
    }

}
