package gestor;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorDeudas gestor = new GestorDeudas();
        String rutaArchivo = "deudas.xlsx"; // Cambiar por la ruta correcta en tu entorno

        gestor.importarDeudasDesdeExcel(rutaArchivo);

        while (true) {
            System.out.println("\\n=== Sistema de Gestión de Deudas ===");
            System.out.println("1. Agregar nueva deuda");
            System.out.println("2. Ver deudas por año");
            System.out.println("3. Ver deudas por tipo");
            System.out.println("4. Ver todas las deudas");
            System.out.println("5. Agregar nuevo tipo de deuda");
            System.out.println("6. Marcar deuda como pagada");
            System.out.println("7. Guardar deudas en Excel");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Monto: ");
                    double monto = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Año: ");
                    int año = scanner.nextInt();
                    scanner.nextLine();

                    List<String> tipos = new ArrayList<>(gestor.getTiposDeuda());
                    for (int i = 0; i < tipos.size(); i++) {
                        System.out.println((i + 1) + ". " + tipos.get(i));
                    }
                    System.out.print("Seleccione tipo: ");
                    int tipoIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (tipoIndex < 0 || tipoIndex >= tipos.size()) {
                        System.out.println("Tipo inválido.");
                        break;
                    }

                    String tipo = tipos.get(tipoIndex);

                    System.out.print("¿Guardar deuda? (acept/cancel): ");
                    String confirmar = scanner.nextLine();
                    if (confirmar.equalsIgnoreCase("acept")) {
                        gestor.agregarDeuda(nombre, monto, año, tipo);
                        System.out.println("Deuda agregada!");
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el año: ");
                    int año2 = scanner.nextInt();
                    scanner.nextLine(); // ← importante para evitar errores al leer después
                    gestor.mostrarDeudasPorAño(año2);
                    break;

                case 3:
                    List<String> tipos3 = new ArrayList<>(gestor.getTiposDeuda());
                    for (int i = 0; i < tipos3.size(); i++) {
                        System.out.println((i + 1) + ". " + tipos3.get(i));
                    }
                    System.out.print("Seleccione tipo: ");
                    int tipoIndex3 = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (tipoIndex3 < 0 || tipoIndex3 >= tipos3.size()) {
                        System.out.println("Tipo inválido.");
                        break;
                    }

                    gestor.mostrarDeudasPorTipo(tipos3.get(tipoIndex3));
                    break;

                case 4:
                    gestor.mostrarTodasLasDeudas();
                    break;

                case 5:
                    System.out.print("Ingrese nuevo tipo de deuda: ");
                    String nuevoTipo = scanner.nextLine();
                    gestor.agregarTipoDeuda(nuevoTipo);
                    System.out.println("Tipo agregado!");
                    break;

                case 6:
                    System.out.print("Ingrese el nombre de la deuda a pagar: ");
                    String nombre6 = scanner.nextLine();
                    List<Deuda> coinciden = new ArrayList<>();
                    for (Deuda d : gestor.getDeudas()) {
                        if (d.getNombre().equalsIgnoreCase(nombre6)) {
                            coinciden.add(d);
                        }
                    }
                    if (coinciden.isEmpty()) {
                        System.out.println("No se encontraron deudas.");
                    } else {
                        for (int i = 0; i < coinciden.size(); i++) {
                            System.out.println(i + ": " + coinciden.get(i));
                        }
                        System.out.print("Seleccione índice para marcar como pagada: ");
                        int indice = scanner.nextInt();
                        scanner.nextLine();
                        if (indice < 0 || indice >= coinciden.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        gestor.marcarDeudaComoPagada(gestor.getDeudas().indexOf(coinciden.get(indice)));
                        System.out.println("¡Deuda marcada como pagada!");
                    }
                    break;

                case 7:
                    gestor.guardarDeudasEnExcel(rutaArchivo);
                    break;

                case 8:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }
}
