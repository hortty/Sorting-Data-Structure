public class ResultadoExecucao {
    
    public long TempExecucao;
    public int NumTrocas;
    public int NumIteracoes;

    public ResultadoExecucao(long TempExecucao, int NumTrocas, int NumInteracoes)
    {
        this.TempExecucao = TempExecucao;
        this.NumTrocas = NumTrocas;
        this.NumIteracoes = NumInteracoes;
    }

    public ResultadoExecucao() {}

}
