package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FoodFilterApp {

    public static void main(String[] args) {
        String folderPath = "C:\\TEMP";
        String fileName = "generic_food.csv";
        File file = new File(folderPath, fileName);

        // Verifica se a pasta e o arquivo existem
        if (!new File(folderPath).exists()) {
            System.out.println("A pasta " + folderPath + " não existe.");
            return;
        }

        if (!file.exists()) {
            System.out.println("O arquivo " + file.getAbsolutePath() + " não existe.");
            return;
        }

        // Lê o arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine(); // Lê a primeira linha (cabeçalho)
            if (header == null) {
                System.out.println("Arquivo está vazio.");
                return;
            }

            // Identifica os índices das colunas
            String[] columns = header.split(",");
            int foodNameIdx = -1;
            int scientificNameIdx = -1;
            int groupIdx = -1;
            int subGroupIdx = -1;

            for (int i = 0; i < columns.length; i++) {
                String col = columns[i].trim().toLowerCase();
                if (col.equals("food name")) foodNameIdx = i;
                else if (col.equals("scientific name")) scientificNameIdx = i;
                else if (col.equals("group")) groupIdx = i;
                else if (col.equals("subgroup")) subGroupIdx = i;
            }

            if (foodNameIdx == -1 || scientificNameIdx == -1 || groupIdx == -1 || subGroupIdx == -1) {
                System.out.println("Cabeçalhos esperados não encontrados.");
                return;
            }

            // Processa o arquivo
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length <= Math.max(Math.max(foodNameIdx, scientificNameIdx), Math.max(groupIdx, subGroupIdx))) {
                    continue; // pula linha mal formatada
                }

                String group = data[groupIdx].trim();
                if (group.equalsIgnoreCase("Fruits")) {
                    String foodName = data[foodNameIdx].trim();
                    String scientificName = data[scientificNameIdx].trim();
                    String subGroup = data[subGroupIdx].trim();

                    System.out.println(foodName + "\t" + scientificName + "\t" + subGroup);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
