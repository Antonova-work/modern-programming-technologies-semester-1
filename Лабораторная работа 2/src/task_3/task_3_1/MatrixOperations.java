package task_3.task_3_1;

public class MatrixOperations {

    // 1. Метод для вывода матрицы
    public static void print(int[][] matrix) {
        if (matrix == null) {
            System.out.println("null");
            return;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // 2. Транспонирование матрицы
    public static int[][] transpose(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    // 3. Умножение матриц
    public static int[][] multiply(int[][] a, int[][] b) {
        // Проверка на null
        if (a == null || b == null) {
            System.err.println("Ошибка: одна из матриц равна null");
            return null;
        }

        // Проверка, что матрицы не пустые
        if (a.length == 0 || b.length == 0 || a[0].length == 0 || b[0].length == 0) {
            System.err.println("Ошибка: матрицы не должны быть пустыми");
            return null;
        }

        // Проверка совместимости размеров: количество столбцов A должно равняться количеству строк B
        int aRows = a.length;
        int aCols = a[0].length;
        int bRows = b.length;
        int bCols = b[0].length;

        if (aCols != bRows) {
            System.err.printf("Ошибка: несовместимые размеры матриц (A: %dx%d, B: %dx%d)%n",
                    aRows, aCols, bRows, bCols);
            return null;
        }

        // Создаем результирующую матрицу
        int[][] result = new int[aRows][bCols];

        // Умножение матриц
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                int sum = 0;
                for (int k = 0; k < aCols; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    // 4. Сумма элементов главной диагонали
    public static int diagonalSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int sum = 0;
        int size = Math.min(matrix.length, matrix[0].length); // Минимальная размерность

        for (int i = 0; i < size; i++) {
            sum += matrix[i][i];
        }

        return sum;
    }

    // Вспомогательный метод для проверки, является ли матрица квадратной
    public static boolean isSquare(int[][] matrix) {
        if (matrix == null) return false;
        return matrix.length == matrix[0].length;
    }

    public static void main(String[] args) {
        int[][] a = {
                {1, 2, 3},
                {4, 5, 6}
        };

        int[][] b = {
                {7, 8},
                {9, 10},
                {11, 12}
        };

        System.out.println("Матрица A (2x3):");
        print(a);

        System.out.println("\nТранспонированная A (3x2):");
        int[][] aTransposed = transpose(a);
        print(aTransposed);

        System.out.println("\nМатрица B (3x2):");
        print(b);

        int[][] c = multiply(a, b);
        System.out.println("\nA * B (2x2):");
        print(c);

        if (c != null) {
            System.out.println("\nСумма диагонали A*B: " + diagonalSum(c));
        }

        // Дополнительные тесты для проверки ошибок
        System.out.println("\n--- Тесты с ошибками ---");

        // Несовместимые размеры
        int[][] wrongB = {{1, 2}, {3, 4}};
        System.out.println("Попытка умножить A (2x3) на B (2x2):");
        int[][] wrongResult = multiply(a, wrongB);

        // Квадратная матрица для проверки диагонали
        int[][] square = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("\nКвадратная матрица 3x3:");
        print(square);
        System.out.println("Сумма главной диагонали: " + diagonalSum(square));
    }
}