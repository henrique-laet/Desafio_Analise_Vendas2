package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();


        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] field = line.split(",");
                list.add(new Sale(Integer.parseInt(field[0]), Integer.parseInt(field[1]), field[2], Integer.parseInt(field[3]), Double.parseDouble(field[4])));
                line = br.readLine();
            }

            System.out.println("Total de vendas por vendedor:");

            List<Sale> sellers = list.stream()
                    .filter(x -> x.getSeller().length() > 0)
                    .collect(Collectors.toList());

            Map<String, Double> sumSales = sellers.stream()
                    .collect(Collectors.groupingBy(
                            x -> x.getSeller(),
                            Collectors.summingDouble(p -> p.getTotal())
                    ));

            sumSales.forEach((seller, total) -> {
                System.out.println(seller + " - R$ " + String.format("%.2f", total));
            });
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
