package two;

import java.util.ArrayList;
import java.util.Scanner;

public class PracticeTwoApp {

    public static void main(String[] args) {
        // Основное хранилище объектов
        ArrayList<Person> people = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Главный цикл
        while (running) {
            printMenu();
            int choice = readIntInRange(scanner, "Выберите пункт меню (1-6): ", 1, 6);

            if (choice == 1) {
                people.add(new Person());
                System.out.println("Пустой объект добавлен.");
            } else if (choice == 2) {
                Person person = createPersonFromInput(scanner);
                people.add(person);
                System.out.println("Объект человек добавлен.");
            } else if (choice == 3) {
                editPerson(scanner, people);
            } else if (choice == 4) {
                searchBySurname(scanner, people);
            } else if (choice == 5) {
                displayAndSortList(scanner, people);
            } else if (choice == 6) {
                running = false;
                System.out.println("Работа программы завершена.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== МЕНЮ ===");
        System.out.println("1. Добавить пустой объект (конструктор по умолчанию)");
        System.out.println("2. Добавить объект с данными пользователя (конструктор с параметрами)");
        System.out.println("3. Редактировать поле объекта");
        System.out.println("4. Расчитать индекс тела по фамилии");
        System.out.println("5. Вывод списка и сортировка");
        System.out.println("6. Выход");
    }
    /** Создать обьект. */
    private static Person createPersonFromInput(Scanner scanner) {
        /**  Последовательно собираем и проверяем все поля объекта */
        String fullName = readNonEmptyLine(scanner, "Введите ФИО: ");
        int age = readIntInRange(scanner, "Введите возраст (0-130): ", 0, 130);
        String gender = readGender(scanner);
        String nationality = readNonEmptyLine(scanner, "Введите национальность: ");
        double heightCm = readDoubleInRange(scanner, "Введите рост в см (50-250): ", 50.0, 250.0);
        double weightKg = readDoubleInRange(scanner, "Введите вес в кг (2-500): ", 2.0, 500.0);

        return new Person(fullName, age, gender, nationality, heightCm, weightKg);
    }


    /** редактировать атрибуты, но сначала проверить не пустой */
    private static void editPerson(Scanner scanner, ArrayList<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Список пуст. Редактировать нечего.");
            return;
        }

        printPeopleList(people);
        int index = readIntInRange(scanner, "Введите индекс объекта: ", 0, people.size() - 1);
        Person person = people.get(index);

        System.out.println("Выберите поле для редактирования:");
        System.out.println("1. ФИО");
        System.out.println("2. Возраст");
        System.out.println("3. Пол");
        System.out.println("4. Национальность");
        System.out.println("5. Рост в см");
        System.out.println("6. Вес в кг");
        int field = readIntInRange(scanner, "Поле (1-6): ", 1, 6);

        boolean changed = false;

        // Меняем только выбранное пользователем поле.
        if (field == 1) {
            String fullName = readNonEmptyLine(scanner, "Введите новое ФИО: ");
            changed = person.setFullName(fullName);
        } else if (field == 2) {
            int age = readIntInRange(scanner, "Введите новый возраст (0-130): ", 0, 130);
            changed = person.setAge(age);
        } else if (field == 3) {
            String gender = readGender(scanner);
            changed = person.setGender(gender);
        } else if (field == 4) {
            String nationality = readNonEmptyLine(scanner, "Введите новую национальность: ");
            changed = person.setNationality(nationality);
        } else if (field == 5) {
            double heightCm =
                    readDoubleInRange(scanner, "Введите новый рост в см (50-250): ", 50.0, 250.0);
            changed = person.setHeightCm(heightCm);
        } else if (field == 6) {
            double weightKg =
                    readDoubleInRange(scanner, "Введите новый вес в кг (2-500): ", 2.0, 500.0);
            changed = person.setWeightKg(weightKg);
        }

        if (changed) {
            System.out.println("Поле обновлено.");
        } else {
            System.out.println("Некорректное значение. Поле не изменено.");
        }
    }

    private static void searchBySurname(Scanner scanner, ArrayList<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        String surnameQuery =
                readNonEmptyLine(scanner, "Введите фамилию для поиска: ").toLowerCase();
        boolean found = false;

        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);
            String surname = extractSurname(person.getFullName());
            if (surname.equals(surnameQuery)) {
                System.out.println("Индекс [" + i + "] | " + formatPerson(person));
                System.out.println(
                        "  Категория физического состояния: " + getPhysicalCondition(person));
                found = true;
            }
        }

        if (!found) {
            System.out.println("Человек с фамилией '" + surnameQuery + "' не найден.");
        }
    }

    private static void displayAndSortList(Scanner scanner, ArrayList<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        System.out.println("\n--- Список объектов (по индексу) ---");
        printPeopleList(people);

        String sortChoice =
                readNonEmptyLine(scanner, "Хотите отсортировать? (да/нет): ").toLowerCase();
        if (sortChoice.equals("да") || sortChoice.equals("д")) {
            sortAndPrintList(scanner, people);
        }
    }

    private static void sortAndPrintList(Scanner scanner, ArrayList<Person> people) {
        if (people.size() < 2) {
            System.out.println("Для сортировки нужно минимум 2 человека.");
            return;
        }

        System.out.println("Сортировать по полю:");
        System.out.println("1. ФИО");
        System.out.println("2. Возраст");
        System.out.println("3. Пол");
        System.out.println("4. Национальность");
        System.out.println("5. Рост в см");
        System.out.println("6. Вес в кг");
        int field = readIntInRange(scanner, "Выберите поле (1-6): ", 1, 6);

        // Сортировка выбором по возрастанию
        for (int i = 0; i < people.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < people.size(); j++) {
                if (isLess(people.get(j), people.get(minIndex), field)) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Person temp = people.get(i);
                people.set(i, people.get(minIndex));
                people.set(minIndex, temp);
            }
        }

        System.out.println("\n--- Отсортированный список ---");
        printPeopleList(people);
    }

    private static boolean isLess(Person left, Person right, int field) {
        if (field == 1) {
            return left.getFullName().compareToIgnoreCase(right.getFullName()) < 0;
        } else if (field == 2) {
            return left.getAge() < right.getAge();
        } else if (field == 3) {
            return left.getGender().compareToIgnoreCase(right.getGender()) < 0;
        } else if (field == 4) {
            return left.getNationality().compareToIgnoreCase(right.getNationality()) < 0;
        } else if (field == 5) {
            return left.getHeightCm() < right.getHeightCm();
        } else {
            return left.getWeightKg() < right.getWeightKg();
        }
    }

    private static void printPeopleList(ArrayList<Person> people) {
        for (int i = 0; i < people.size(); i++) {
            System.out.println("[" + i + "] | " + formatPerson(people.get(i)));
        }
    }

    private static String formatPerson(Person person) {
        return "ФИО: " + person.getFullName() + ", возраст: " + person.getAge() + ", пол: "
                + person.getGender() + ", национальность: " + person.getNationality()
                + ", рост (см): " + person.getHeightCm() + ", вес (кг): " + person.getWeightKg();
    }

    private static String extractSurname(String fullName) {
        String normalized = fullName.trim().toLowerCase();
        int spaceIndex = normalized.indexOf(' ');
        if (spaceIndex == -1) {
            return normalized;
        }
        return normalized.substring(0, spaceIndex);
    }

    private static String readNonEmptyLine(Scanner scanner, String prompt) {
        // Повторяем ввод, пока строка не станет непустой.
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Поле не может быть пустым.");
        }
    }

    private static int readIntInRange(Scanner scanner, String prompt, int min, int max) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!isIntegerString(input)) {
                System.out.println("Введите корректное целое число.");
                continue;
            }

            int value = parseIntWithoutException(input);
            if (value < min || value > max) {
                System.out.println("Значение должно быть в диапазоне [" + min + ", " + max + "].");
                continue;
            }
            return value;
        }
    }

    private static double readDoubleInRange(Scanner scanner, String prompt, double min,
            double max) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().replace(',', '.');

            if (!isPositiveDecimalString(input)) {
                System.out.println("Введите корректное положительное число.");
                continue;
            }

            double value = parsePositiveDoubleWithoutException(input);
            if (value < min || value > max) {
                System.out.println("Значение должно быть в диапазоне [" + min + ", " + max + "].");
                continue;
            }
            return value;
        }
    }

    private static String readGender(Scanner scanner) {

        while (true) {
            System.out.print("Введите пол (мужской/женский): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("мужской") || input.equals("женский")) {
                return input;
            }
            System.out.println("Некорректное значение. Допустимо: мужской или женский.");
        }
    }

    private static boolean isIntegerString(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        int start = 0;
        if (value.charAt(0) == '-') {
            if (value.length() == 1) {
                return false;
            }
            start = 1;
        }
        for (int i = start; i < value.length(); i++) {
            if (value.charAt(i) < '0' || value.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static int parseIntWithoutException(String value) {
        // Ручной разбор целого числа посимвольно.
        boolean negative = value.charAt(0) == '-';
        int start = negative ? 1 : 0;
        long result = 0;

        for (int i = start; i < value.length(); i++) {
            result = result * 10 + (value.charAt(i) - '0');
        }

        if (negative) {
            result = -result;
        }
        return (int) result;
    }

    private static boolean isPositiveDecimalString(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        int dots = 0;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '.') {
                dots++;
                if (dots > 1) {
                    return false;
                }
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return !(value.equals("."));
    }

    private static double parsePositiveDoubleWithoutException(String value) {
        // Ручной разбор десятичного, посоветовала нейронка
        double integerPart = 0.0;
        double fractionPart = 0.0;
        double divisor = 10.0;
        boolean afterDot = false;

        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '.') {
                afterDot = true;
                continue;
            }

            int digit = ch - '0';
            if (!afterDot) {
                integerPart = integerPart * 10 + digit;
            } else {
                fractionPart += digit / divisor;
                divisor *= 10.0;
            }
        }

        return integerPart + fractionPart;
    }

    /**
     * Считает параметры человека.
     *
     * @param person
     * @return возвращает категорию по весу
     */
    private static String getPhysicalCondition(Person person) {
        int age = person.getAge();
        double heightCm = person.getHeightCm();
        double weightKg = person.getWeightKg();

        if (age < 0 || heightCm <= 0 || weightKg <= 0) {
            return "недостаточно данных";
        }
        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);

        if (age < 18) {
            if (bmi < 17.5) {
                return "недовес (для несовершеннолетнего)";
            } else if (bmi <= 24.0) {
                return "норма (для несовершеннолетнего)";
            } else {
                return "избыточный вес (для несовершеннолетнего)";
            }
        }

        if (bmi < 18.5) {
            return "недовес";
        } else if (bmi < 25.0) {
            return "норма";
        } else if (bmi < 30.0) {
            return "избыточный вес";
        } else {
            return "ожирение";
        }
    }
}
