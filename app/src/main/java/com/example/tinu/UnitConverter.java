package com.example.tinu;

import java.util.List;

public class UnitConverter {

    // MÉTODO UNIFICADO PRINCIPAL - converte qualquer unidade usando grandeza
    public static double converter(double valor, UnitOptions.grandeza tipo, int origem, int destino) {
        if (origem == destino) return valor;

        switch (tipo) {
            case COMPRIMENTO: return converterComprimento(valor, origem, destino);
            case AREA: return converterArea(valor, origem, destino);
            case TEMPERATURA: return converterTemperatura(valor, origem, destino);
            case VOLUME: return converterVolume(valor, origem, destino);
            case MASSA: return converterMassa(valor, origem, destino);
            case DADOS: return converterDados(valor, origem, destino);
            case VELOCIDADE: return converterVelocidade(valor, origem, destino);
            case TEMPO: return converterTempo(valor, origem, destino);
            default: return valor;
        }
    }

    // FORMATAÇÃO UNIFICADA - usa automaticamente as abreviações do UnitOptions
    public static String formatar(double valor, UnitOptions.grandeza tipo, int indice) {
        String unidade = "";
        switch (tipo) {
            case COMPRIMENTO: unidade = UnitOptions.comprimentoAbrvAt(indice); break;
            case AREA: unidade = UnitOptions.areaAbrvAt(indice); break;
            case TEMPERATURA: unidade = UnitOptions.temperaturaAbrvAt(indice); break;
            case VOLUME: unidade = UnitOptions.volumeAbrvAt(indice); break;
            case MASSA: unidade = UnitOptions.massaAbrvAt(indice); break;
            case DADOS: unidade = UnitOptions.dadosAbrvAt(indice); break;
            case VELOCIDADE: unidade = UnitOptions.velocidadeAbrvAt(indice); break;
            case TEMPO: unidade = UnitOptions.tempoAbrvAt(indice); break;
        }
        return String.format("%.2f %s", valor, unidade);
    }



    // CONVERSÃO POR NOMES - usa diretamente as listas do UnitOptions
    public static double converterPorNome(double valor, String tipoGrandeza, String unidadeDe, String unidadePara) {
        List<String> unidades = UnitOptions.getUnidadesFromString(tipoGrandeza);
        if (unidades == null) return valor;

        int idxDe = unidades.indexOf(unidadeDe);
        int idxPara = unidades.indexOf(unidadePara);

        if (idxDe == -1 || idxPara == -1) return valor;

        UnitOptions.grandeza tipo = UnitOptions.getGrandezaFromString(tipoGrandeza);
        if (tipo == null) return valor;

        return converter(valor, tipo, idxDe, idxPara);
    }

    // MÉTODOS INTERNOS DE CONVERSÃO (mantidos para funcionalidade)
    private static double converterComprimento(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraMetros = {0.001, 0.01, 1.0, 1000.0, 0.0254, 0.3048, 1609.34};
        return valor * paraMetros[origem] / paraMetros[destino];
    }

    private static double converterArea(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraM2 = {4046.86, 10000.0, 0.0001, 0.092903, 0.00064516, 1.0};
        return valor * paraM2[origem] / paraM2[destino];
    }

    private static double converterTemperatura(double valor, int origem, int destino) {
        if (origem == destino) return valor;

        if (origem == 0 && destino == 1) return (valor * 9.0/5.0) + 32; // C → F
        if (origem == 0 && destino == 2) return valor + 273.15; // C → K
        if (origem == 1 && destino == 0) return (valor - 32) * 5.0/9.0; // F → C
        if (origem == 1 && destino == 2) return ((valor - 32) * 5.0/9.0) + 273.15; // F → K
        if (origem == 2 && destino == 0) return valor - 273.15; // K → C
        if (origem == 2 && destino == 1) return ((valor - 273.15) * 9.0/5.0) + 32; // K → F

        return valor;
    }

    private static double converterVolume(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraLitros = {3.78541, 1.0, 0.001, 0.000001, 1000.0, 0.0163871, 28.3168};
        return valor * paraLitros[origem] / paraLitros[destino];
    }

    private static double converterMassa(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraKg = {1000.0, 0.453592, 0.0283495, 1.0, 0.001};
        return valor * paraKg[origem] / paraKg[destino];
    }

    private static double converterDados(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraBits = {1.0, 8.0, 8192.0, 8388608.0, 8589934592.0, 8796093022208.0};
        return valor * paraBits[origem] / paraBits[destino];
    }

    private static double converterVelocidade(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraMPS = {1.0, 2.77778e-4, 1000.0, 0.277778, 0.3048, 8.46667e-5, 1609.34, 0.44704};
        return valor * paraMPS[origem] / paraMPS[destino];
    }

    private static double converterTempo(double valor, int origem, int destino) {
        if (origem == destino) return valor;
        double[] paraSegundos = {0.001, 1.0, 60.0, 3600.0, 86400.0, 604800.0};
        return valor * paraSegundos[origem] / paraSegundos[destino];
    }
}
