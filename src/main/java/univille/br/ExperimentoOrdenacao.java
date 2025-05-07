package univille.br;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class ExperimentoOrdenacao {
    
    private static final int NUM_REPETICOES = 30;
    private static final DecimalFormat df = new DecimalFormat("0.000");
    
    // Tipos de ordenação inicial do array
    public enum TipoOrdenacao {
        ALEATORIO,
        CRESCENTE,
        DECRESCENTE
    }
    
    // Gera um array com distribuição específica
    public static int[] gerarArray(int tamanho, TipoOrdenacao tipo) {
        int[] array = new int[tamanho];
        
        switch (tipo) {
            case ALEATORIO:
                Random random = new Random();
                for (int i = 0; i < tamanho; i++) {
                    array[i] = random.nextInt(1000000); // Números aleatórios entre 0 e 999999
                }
                break;
                
            case CRESCENTE:
                for (int i = 0; i < tamanho; i++) {
                    array[i] = i;
                }
                break;
                
            case DECRESCENTE:
                for (int i = 0; i < tamanho; i++) {
                    array[i] = tamanho - i - 1;
                }
                break;
        }
        
        return array;
    }
    
    // Método para copiar um array
    public static int[] copiarArray(int[] original) {
        return Arrays.copyOf(original, original.length);
    }
    
    // BubbleSort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    // SelectionSort
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
    
    // InsertionSort
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    // MergeSort
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
    
    // QuickSort
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
    
    // Método para verificar se o array está ordenado
    public static boolean isOrdenado(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    // Método para calcular média
    public static double calcularMedia(long[] tempos) {
        long soma = 0;
        for (long tempo : tempos) {
            soma += tempo;
        }
        return (double) soma / tempos.length;
    }
    
    // Método para calcular desvio padrão
    public static double calcularDesvioPadrao(long[] tempos, double media) {
        double soma = 0;
        for (long tempo : tempos) {
            soma += Math.pow(tempo - media, 2);
        }
        return Math.sqrt(soma / tempos.length);
    }
    
    // Método para executar um algoritmo várias vezes e retornar estatísticas
    public static double[] executarExperimento(int[] arrayOriginal, String algoritmo) {
        long[] tempos = new long[NUM_REPETICOES];
        
        for (int i = 0; i < NUM_REPETICOES; i++) {
            int[] array = copiarArray(arrayOriginal);
            
            long inicio = System.nanoTime();
            
            switch (algoritmo) {
                case "BubbleSort":
                    bubbleSort(array);
                    break;
                case "SelectionSort":
                    selectionSort(array);
                    break;
                case "InsertionSort":
                    insertionSort(array);
                    break;
                case "MergeSort":
                    mergeSort(array, 0, array.length - 1);
                    break;
                case "QuickSort":
                    quickSort(array, 0, array.length - 1);
                    break;
            }
            
            long fim = System.nanoTime();
            long tempoExecucao = (fim - inicio) / 1_000_000; // Converter para milissegundos
            
            tempos[i] = tempoExecucao; // <-- Adicione esta linha para salvar o tempo!

            if (!isOrdenado(array)) {
                System.err.println("ERRO: Algoritmo " + algoritmo + " não ordenou corretamente!");
                break;
            }
        }
        
        double media = calcularMedia(tempos);
        double desvioPadrao = calcularDesvioPadrao(tempos, media);
        
        return new double[]{media, desvioPadrao};
    }
    
    // Método para salvar resultados em CSV
    public static void salvarResultados(String nomeArquivo, String[][] resultados) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            // Cabeçalho
            writer.append("Algoritmo,Tamanho,Distribuição,Média (ms),Desvio Padrão (ms)\n");
            
            // Dados
            for (String[] linha : resultados) {
                writer.append(String.join(",", linha));
                writer.append("\n");
            }
            
            System.out.println("Resultados salvos em " + nomeArquivo);
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar resultados: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // Definir os tamanhos dos arrays
        int[] tamanhos = {1000, 10000};
        
        // Definir os tipos de distribuição
        TipoOrdenacao[] distribuicoes = {
            TipoOrdenacao.ALEATORIO,
            TipoOrdenacao.CRESCENTE,
            TipoOrdenacao.DECRESCENTE
        };
        
        // Definir os algoritmos
        String[] algoritmos = {
            "BubbleSort",
            "SelectionSort",
            "InsertionSort",
            "MergeSort",
            "QuickSort"
        };
        
        // Matriz para armazenar os resultados
        String[][] resultados = new String[tamanhos.length * distribuicoes.length * algoritmos.length][5];
        int linhaResultado = 0;
        
        // Executar experimentos
        for (int tamanho : tamanhos) {
            System.out.println("\n--- Iniciando experimentos com arrays de tamanho " + tamanho + " ---");
            
            for (TipoOrdenacao distribuicao : distribuicoes) {
                System.out.println("\nDistribuição: " + distribuicao);
                
                // Gerar o array original com a distribuição específica
                int[] arrayOriginal = gerarArray(tamanho, distribuicao);
                
                for (String algoritmo : algoritmos) {
                    System.out.println("Executando " + algoritmo + "...");
                    
                    // Executar o experimento
                    double[] estatisticas = executarExperimento(arrayOriginal, algoritmo);
                    double media = estatisticas[0];
                    double desvioPadrao = estatisticas[1];
                    
                    // Armazenar resultados
                    resultados[linhaResultado][0] = algoritmo;
                    resultados[linhaResultado][1] = String.valueOf(tamanho);
                    resultados[linhaResultado][2] = distribuicao.toString();
                    resultados[linhaResultado][3] = df.format(media);
                    resultados[linhaResultado][4] = df.format(desvioPadrao);
                    
                    System.out.println("  Média: " + df.format(media) + " ms");
                    System.out.println("  Desvio Padrão: " + df.format(desvioPadrao) + " ms");
                    
                    linhaResultado++;
                }
            }
        }
        
        // Salvar resultados em arquivo CSV
        salvarResultados("resultados_ordenacao.csv", resultados);
        
        System.out.println("\nTodos os experimentos foram concluídos!");
    }
}