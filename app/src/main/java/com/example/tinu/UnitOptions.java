package com.example.tinu;

import java.util.List;

public class UnitOptions {

    public enum grandeza {
        AREA, COMPRIMENTO, TEMPERATURA, VOLUME, MASSA, DADOS, VELOCIDADE, TEMPO
    }

    public enum area {
        AC, HA, CM, FT, IN, M
    }
    public enum comprimento {
        MM, CM, M, KM, IN, FT, MI
    }
    public enum temperatura {
        C, F, K
    }
    public enum volume {
        GAL, L, ML, CM, M, IN, FT
    }
    public enum massa {
        T, LB, OZ, KG, G
    }
    public enum dados {
        BIT, B, KB, MB, GB, TB
    }
    public enum velocidade {
        MPS, MPH, KPS, KPH, FTPS, FTPH, MIPS, MIPH
    }
    public enum tempo {
        MS, S, MIN, H, D, WK
    }

    public static String areaAt(int opt) {
        String[] opts = {"Ácres", "Hectares", "Centímetros quadrados", "Pés quadrados", "Polegadas quadradas", "Metros quadrados"};
        return opts[opt];
    }
    public static String areaAbrvAt(int opt) {
        String[] opts = {"ac", "ha", "cm²", "ft²", "in²", "m²"};
        return opts[opt];
    }

    public static String comprimentoAt(int opt) {
        String[] opts = {"Milímetros", "Centímetros", "Metros", "Quilômetros", "Polegadas", "Pés", "Milhas"};
        return opts[opt];
    }
    public static String comprimentoAbrvAt(int opt) {
        String[] opts = {"mm", "cm", "m", "km", "in", "ft", "mi"};
        return opts[opt];
    }

    public static String temperaturaAt(int opt) {
        String[] opts = {"Celsius", "Fahrenheit", "Kelvin"};
        return opts[opt];
    }
    public static String temperaturaAbrvAt(int opt) {
        String[] opts = {"°C", "°F", "°K"};
        return opts[opt];
    }

    public static String volumeAt(int opt) {
        String[] opts = {"Galões", "Litros", "Mililitros", "Centímetros cúbicos", "Metros cúbicos", "Polegadas cúbicas", "Pés Quadrados"};
        return opts[opt];
    }
    public static String volumeAbrvAt(int opt) {
        String[] opts = {"gal", "l", "ml", "cm³", "m³", "in³", "ft³"};
        return opts[opt];
    }

    public static String massaAt(int opt) {
        String[] opts = {"Toneladas", "Libras", "Onças", "Quilogramas", "Gramas"};
        return opts[opt];
    }
    public static String massaAbrvAt(int opt) {
        String[] opts = {"t", "lb", "oz", "kg", "g"};
        return opts[opt];
    }

    public static String dadosAt(int opt) {
        String[] opts = {"Bit", "Byte", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes"};
        return opts[opt];
    }
    public static String dadosAbrvAt(int opt) {
        String[] opts = {"bit", "B", "KB", "MB", "GB", "TB"};
        return opts[opt];
    }

    public static String velocidadeAt(int opt) {
        String[] opts = {"Metros por segundo", "Metros por hora", "Quilômetros por segundo", "Quilômetros por hora", "Pés por segundo", "Pés por hora", "Milhas por segundo", "Milhas por hora"};
        return opts[opt];
    }
    public static String velocidadeAbrvAt(int opt) {
        String[] opts = {"m/s", "m/h", "k/s", "k/h", "ft/s", "ft/h", "mi/s", "mi/h"};
        return opts[opt];
    }

    public static String tempoAt(int opt) {
        String[] opts = {"Milissegundos", "Segundos", "Minutos", "Horas", "Dias", "Semanas"};
        return opts[opt];
    }
    public static String tempoAbrvAt(int opt) {
        String[] opts = {"ms", "s", "min", "h", "d", "wk"};
        return opts[opt];
    }


    public static List<String> getAreas() {
        return List.of("Ácres", "Hectares", "Centímetros quadrados", "Pés quadrados", "Polegadas quadradas", "Metros quadrados");
    }

    public static List<String> getComprimentos() {
        return List.of("Milímetros", "Centímetros", "Metros", "Quilômetros", "Polegadas", "Pés", "Milhas");
    }

    public static List<String> getTemperaturas() {
        return List.of("Celsius", "Fahrenheit", "Kelvin");
    }

    public static List<String> getVolumes() {
        return List.of("Galões", "Litros", "Mililitros", "Centímetros cúbicos", "Metros cúbicos", "Polegadas cúbicas", "Pés Quadrados");
    }

    public static List<String> getMassas() {
        return List.of("Toneladas", "Libras", "Onças", "Quilogramas", "Gramas");
    }

    public static List<String> getDados() {
        return List.of("Bit", "Byte", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes");
    }

    public static List<String> getVelocidades() {
        return List.of("Metros por segundo", "Metros por hora", "Quilômetros por segundo", "Quilômetros por hora", "Pés por segundo", "Pés por hora", "Milhas por segundo", "Milhas por hora");
    }

    public static List<String> getTempos() {
        return List.of("Milissegundos", "Segundos", "Minutos", "Horas", "Dias", "Semanas");
    }

    public static Enum<?> getEnumFromNome(String nome) {
        for (int i = 0; i < area.values().length; i++) {
            if (areaAt(i).equalsIgnoreCase(nome)) return area.values()[i];
        }
        for (int i = 0; i < comprimento.values().length; i++) {
            if (comprimentoAt(i).equalsIgnoreCase(nome)) return comprimento.values()[i];
        }
        for (int i = 0; i < temperatura.values().length; i++) {
            if (temperaturaAt(i).equalsIgnoreCase(nome)) return temperatura.values()[i];
        }
        for (int i = 0; i < volume.values().length; i++) {
            if (volumeAt(i).equalsIgnoreCase(nome)) return volume.values()[i];
        }
        for (int i = 0; i < massa.values().length; i++) {
            if (massaAt(i).equalsIgnoreCase(nome)) return massa.values()[i];
        }
        for (int i = 0; i < dados.values().length; i++) {
            if (dadosAt(i).equalsIgnoreCase(nome)) return dados.values()[i];
        }
        for (int i = 0; i < velocidade.values().length; i++) {
            if (velocidadeAt(i).equalsIgnoreCase(nome)) return velocidade.values()[i];
        }
        for (int i = 0; i < tempo.values().length; i++) {
            if (tempoAt(i).equalsIgnoreCase(nome)) return tempo.values()[i];
        }
        return null;
    }

    public static String getNomeFromEnum(Enum<?> unidadeEnum) {
        if (unidadeEnum instanceof area) {
            return areaAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof comprimento) {
            return comprimentoAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof temperatura) {
            return temperaturaAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof volume) {
            return volumeAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof massa) {
            return massaAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof dados) {
            return dadosAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof velocidade) {
            return velocidadeAt(unidadeEnum.ordinal());
        } else if (unidadeEnum instanceof tempo) {
            return tempoAt(unidadeEnum.ordinal());
        } else {
            return "Desconhecido";
        }
    }

    public static List<String> getUnidadesFromString(String unidade) {
        switch (unidade.toLowerCase()) {
            case "área": return getAreas();
            case "comprimento": return getComprimentos();
            case "temperatura": return getTemperaturas();
            case "volume": return getVolumes();
            case "massa": return getMassas();
            case "dados": return getDados();
            case "velocidade": return getVelocidades();
            case "tempo": return getTempos();
            default: return getAreas();
        }
    }

    public static List<String> getUnidades(grandeza unidade) {
        switch (unidade) {
            case AREA: return getAreas();
            case COMPRIMENTO: return getComprimentos();
            case TEMPERATURA: return getTemperaturas();
            case VOLUME: return getVolumes();
            case MASSA: return getMassas();
            case DADOS: return getDados();
            case VELOCIDADE: return getVelocidades();
            case TEMPO: return getTempos();
            default: return getAreas();
        }
    }

    public static grandeza parseGrandeza(String label) {
        switch (label.toLowerCase()) {
            case "área": return grandeza.AREA;
            case "comprimento": return grandeza.COMPRIMENTO;
            case "temperatura": return grandeza.TEMPERATURA;
            case "volume": return grandeza.VOLUME;
            case "massa": return grandeza.MASSA;
            case "dados": return grandeza.DADOS;
            case "velocidade": return grandeza.VELOCIDADE;
            case "tempo": return grandeza.TEMPO;
            default: return grandeza.AREA;
        }
    }

    public static String getAbbreviationFromNome(String nome) {
        for (int i = 0; i < area.values().length; i++) {
            if (areaAt(i).equalsIgnoreCase(nome)) return areaAbrvAt(i);
        }
        for (int i = 0; i < comprimento.values().length; i++) {
            if (comprimentoAt(i).equalsIgnoreCase(nome)) return comprimentoAbrvAt(i);
        }
        for (int i = 0; i < temperatura.values().length; i++) {
            if (temperaturaAt(i).equalsIgnoreCase(nome)) return temperaturaAbrvAt(i);
        }
        for (int i = 0; i < volume.values().length; i++) {
            if (volumeAt(i).equalsIgnoreCase(nome)) return volumeAbrvAt(i);
        }
        for (int i = 0; i < massa.values().length; i++) {
            if (massaAt(i).equalsIgnoreCase(nome)) return massaAbrvAt(i);
        }
        for (int i = 0; i < dados.values().length; i++) {
            if (dadosAt(i).equalsIgnoreCase(nome)) return dadosAbrvAt(i);
        }
        for (int i = 0; i < velocidade.values().length; i++) {
            if (velocidadeAt(i).equalsIgnoreCase(nome)) return velocidadeAbrvAt(i);
        }
        for (int i = 0; i < tempo.values().length; i++) {
            if (tempoAt(i).equalsIgnoreCase(nome)) return tempoAbrvAt(i);
        }
        return null;
    }
}
