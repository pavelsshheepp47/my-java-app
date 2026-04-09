import java.util.Scanner;
import java.util.Random;

public class MatrixApp {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    // Переменные для хранения данных
    int size = 0;
    int[][] matrixA = null;
    int[][] matrixB = null;
    int[][] matrixC = null;
    int determinant = 0;

    // Флаги состояния
    boolean dataEntered = false;
    boolean algorithmDone = false;

    int choice = 0;
    boolean validInput = false;

    do {
      // Вывод меню
      System.out.println("\n========== МЕНЮ ==========");
      System.out.println("1. Ввод исходных данных");
      System.out.println("2. Выполнить алгоритм");
      System.out.println("3. Вывести результат");
      System.out.println("4. Выход");
      System.out.println("==========================");
      System.out.print("Выберите пункт: ");

      // Проверка: введена ли цифра?
      if (scanner.hasNextInt()) {
        choice = scanner.nextInt();
        validInput = true;
      } else {
        // Если ввели не цифру, очищаем ввод и показываем ошибку
        String wrongInput = scanner.next();
        System.out.println("Ошибка! Введите число от 1 до 4. Вы ввели: " + wrongInput);
        validInput = false;
        continue;
      }

      if (!validInput) {
        continue;
      }

      if (choice == 1) {
        // Пункт 1: Ввод данных
        System.out.println("\n--- Ввод исходных данных ---");

        // Ввод размера матрицы в формате "2x2" или "2 x 2" или просто "2"
        System.out.print("Введите размер матрицы (N x N), N = ");
        size = readMatrixSize(scanner);

        if (size == -1) {
          continue;
        }

        // Защита от дурака
        if (size < 1 || size > 10) {
          System.out.println("Ошибка! Размер должен быть от 1 до 10. Попробуйте снова.");
          continue;
        }

        // Создаем матрицы нужного размера
        matrixA = new int[size][size];
        matrixB = new int[size][size];

        System.out.println("Выберите способ заполнения:");
        System.out.println("  1 - Ввести вручную");
        System.out.println("  2 - Случайные числа");
        System.out.print("Ваш выбор: ");

        // Проверка ввода способа заполнения
        int method = 0;
        if (scanner.hasNextInt()) {
          method = scanner.nextInt();
        } else {
          System.out.println("Ошибка! Введите 1 или 2.");
          scanner.next();
          continue;
        }

        if (method == 1) {
          // Ручной ввод матрицы A
          System.out.println("\nВведите элементы матрицы A:");
          for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
              System.out.print("A[" + i + "][" + j + "] = ");
              if (scanner.hasNextInt()) {
                matrixA[i][j] = scanner.nextInt();
              } else {
                System.out.println("Ошибка! Введите целое число.");
                scanner.next();
                j--; // Повторяем ввод для этого элемента
              }
            }
          }

          // Ручной ввод матрицы B
          System.out.println("\nВведите элементы матрицы B:");
          for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
              System.out.print("B[" + i + "][" + j + "] = ");
              if (scanner.hasNextInt()) {
                matrixB[i][j] = scanner.nextInt();
              } else {
                System.out.println("Ошибка! Введите целое число.");
                scanner.next();
                j--;
              }
            }
          }
        } else if (method == 2) {
          // Случайная генерация
          System.out.println("\nГенерация случайных чисел (от -10 до 10)...");
          for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
              matrixA[i][j] = random.nextInt(21) - 10;
              matrixB[i][j] = random.nextInt(21) - 10;
            }
          }
        } else {
          System.out.println("Ошибка! Выберите 1 или 2.");
          continue;
        }

        // Выводим введенные матрицы
        System.out.println("\nМатрица A:");
        printMatrix(matrixA);

        System.out.println("\nМатрица B:");
        printMatrix(matrixB);

        dataEntered = true;
        algorithmDone = false;
        System.out.println("\nДанные успешно введены!");

      } else if (choice == 2) {
        // Пункт 2: Выполнение алгоритма
        System.out.println("\n--- Выполнение алгоритма ---");

        if (!dataEntered) {
          System.out.println("Ошибка! Сначала введите данные (пункт 1).");
          continue;
        }

        // Вычисляем сумму матриц C = A + B
        matrixC = new int[size][size];
        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            matrixC[i][j] = matrixA[i][j] + matrixB[i][j];
          }
        }

        // Вычисляем определитель
        determinant = calculateDeterminant(matrixC, size);

        algorithmDone = true;
        System.out.println("Алгоритм выполнен!");
        System.out.println("Определитель матрицы C = " + determinant);

      } else if (choice == 3) {
        // Пункт 3: Вывод результата
        System.out.println("\n--- Вывод результата ---");

        if (!dataEntered) {
          System.out.println("Ошибка! Сначала введите данные (пункт 1).");
          continue;
        }

        if (!algorithmDone) {
          System.out.println("Ошибка! Сначала выполните алгоритм (пункт 2).");
          continue;
        }

        System.out.println("\n========== РЕЗУЛЬТАТ ==========");
        System.out.println("Матрица C = A + B:");
        printMatrix(matrixC);
        System.out.println("\nОпределитель матрицы C: " + determinant);
        System.out.println("================================\n");

      } else if (choice == 4) {
        System.out.println("До свидания!");
      } else {
        System.out.println("Неверный выбор! Введите число от 1 до 4.");
      }

    } while (choice != 4);

    scanner.close();
  }

  // Функция для чтения размера матрицы в разных форматах
  public static int readMatrixSize(Scanner scanner) {
    String input = scanner.nextLine();

    // Если строка пустая, читаем следующую
    if (input.trim().isEmpty()) {
      input = scanner.nextLine();
    }

    input = input.trim().toLowerCase();

    // Проверяем формат "2x2" или "2 x 2"
    if (input.contains("x")) {
      // Разбиваем по букве x
      String[] parts = input.split("x");
      if (parts.length >= 2) {
        try {
          // Берем первую часть (число)
          String firstPart = parts[0].trim();
          int size = Integer.parseInt(firstPart);
          return size;
        } catch (NumberFormatException e) {
          System.out.println("Ошибка! Неверный формат. Пример: 2x2 или 2");
          return -1;
        }
      }
    }

    // Пробуем прочитать просто число
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      System.out.println("Ошибка! Введите число или формат 'NxN' (например, 2x2)");
      return -1;
    }
  }

  // Функция для вывода матрицы
  public static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  // Функция вычисления определителя
  public static int calculateDeterminant(int[][] matrix, int n) {
    if (n == 1) {
      return matrix[0][0];
    }

    if (n == 2) {
      return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    int result = 0;
    int sign = 1;

    for (int col = 0; col < n; col++) {
      int[][] minor = new int[n - 1][n - 1];

      for (int i = 1; i < n; i++) {
        int minorCol = 0;
        for (int j = 0; j < n; j++) {
          if (j != col) {
            minor[i - 1][minorCol] = matrix[i][j];
            minorCol++;
          }
        }
      }

      int minorDet = calculateDeterminant(minor, n - 1);
      result = result + sign * matrix[0][col] * minorDet;
      sign = -sign;
    }

    return result;
  }
}
