public class Main {
    public static void main(String[] args) {
        System.out.println("#################################################################");
        System.out.println("# INSTRUCCIONES PARA COMPILAR Y EJECUTAR LA APLICACION DISTRIBUIDA #");
        System.out.println("#################################################################");
        System.out.println();
        System.out.println("Este proyecto ha sido reestructurado a una arquitectura de 3 capas:");
        System.out.println("Cliente -> ServidorPrincipal -> ServidorFibonacci -> Workers");
        System.out.println();
        System.out.println("Siga estos pasos para ejecutarlo correctamente:");
        System.out.println();
        System.out.println("PASO 1: COMPILAR TODOS LOS ARCHIVOS");
        System.out.println("------------------------------------");
        System.out.println("Abra una terminal en el directorio raiz del proyecto y ejecute:");
        System.out.println("javac *.java");
        System.out.println();
        System.out.println("PASO 2: EJECUTAR LOS COMPONENTES EN EL ORDEN CORRECTO");
        System.out.println("------------------------------------------------------");
        System.out.println("Debera abrir una terminal SEPARADA para cada componente.");
        System.out.println();
        System.out.println("1. Inicie el ServidorFibonacci (el gestor de tareas):");
        System.out.println("   java ServidorFibonacci");
        System.out.println("   -> Este servidor escuchara en los puertos 4444 (para el servidor principal) y 4445 (para los workers).");
        System.out.println();
        System.out.println("2. Inicie uno o mas Workers (los que calculan Fibonacci):");
        System.out.println("   java WorkerFibonacci");
        System.out.println("   -> Puede ejecutar este comando en varias terminales para simular multiples workers.");
        System.out.println();
        System.out.println("3. Inicie el ServidorPrincipal (el punto de entrada):");
        System.out.println("   java ServidorPrincipal");
        System.out.println("   -> Este servidor escuchara al cliente principal en el puerto 4443.");
        System.out.println();
        System.out.println("4. Inicie el ClientePrincipal (el que envia los numeros):");
        System.out.println("   java ClientePrincipal");
        System.out.println("   -> Este cliente comenzara a enviar numeros aleatorios al ServidorPrincipal.");
        System.out.println();
        System.out.println("Ahora deberia ver los logs en cada terminal, mostrando como los numeros son enviados,");
        System.out.println("reenviados, asignados a un worker y finalmente calculados.");
        System.out.println("#################################################################");
    }
}