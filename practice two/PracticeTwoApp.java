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
                System.out.println("Объект Person добавлен.");
            } else if (choice == 3) {
                editPerson(scanner, people);
            } else if (choice == 4) {
                checkPhysicalCondition(scanner, people);
            } else if (choice == 5) {
                sortPeopleByField(scanner, people);
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
        System.out.println("3. Редактировать поле объекта по индексу");
        System.out.println("4. Проверить состояние человека (по индексу, по фамилии, для всех)");
        System.out.println("5. Отсортировать объекты по выбранному полю (по возрастанию)");
        System.out.println("6. Выход");
    }

    private static Person createPersonFromInput(Scanner scanner) {
        // Последовательно собираем и проверяем все поля объекта.
        String fullName = readNonEmptyLine(scanner, "Введите ФИО: ");
        int age = readIntInRange(scanner, "Введите возраст (0-130): ", 0, 130);
        String gender = readGender(scanner);
        String nationality = readNonEmptyLine(scanner, "Введите национальность: ");
        double heightCm = readDoubleInRange(scanner, "Введите рост в см (50-250): ", 50.0, 250.0);
        double weightKg = readDoubleInRange(scanner, "Введите вес в кг (2-500): ", 2.0, 500.0);

        return new Person(fullName, age, gender, nationality, heightCm, weightKg);
    }

    private static void editPerson(Scanner scanner, ArrayList<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Список пуст. Редактировать нечего.");
            return;
        }

        printIndexList(people);
        int index = readIntInRange(scanner, "Введите индекс объекта: ", 0, people.size() - 1);
        Person person = people.get(index);

        System.out.println("Выберите поле для редактирования:");
        System.out.println("1. ФИО (fullName)");
        System.out.println("2. Возраст (age)");
        System.out.println("3. Пол (gender)");
        System.out.println("4. Национальность (nationality)");
        System.out.println("5. Рост в см (heightCm)");
        System.out.println("6. Вес в кг (weightKg)");
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
            double heightCm = readDoubleInRange(scanner, "Введите новый рост в см (50-250): ", 50.0, 250.0);
            changed = person.setHeightCm(heightCm);
        } else if (field == 6) {
            double weightKg = readDoubleInRange(scanner, "Введите новый вес в кг (2-500): ", 2.0, 500.0);
            changed = person.setWeightKg(weightKg);
        }

        if (changed) {
            System.out.println("Поле обновлено.");
        } else {
            System.out.println("Некорректное значение. Поле не изменено.");
        }
    }

    private static void checkPhysicalCondition(Scanner scanner, ArrayList<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        System.out.println("Проверка состояния:");
        System.out.println("1. По индексу человека");
        System.out.println("2. Поиск по фамилии");
        System.out.println("3. Для всех людей");
        int mode = readIntInRange(scanner, "Выберите режим (1-3): ", 1, 3);

        if (mode == 1) {
            printIndexList(people);
            int index = readIntInRange(scanner, "Введите индекс человека: ", 0, people.size() - 1);
            printPersonCondition(index, people.get(index));
        } else if (mode == 2) {
            String surnameQuery = readNonEmptyLine(scanner, "Введите фамилию для поиска: ").toLowerCase();
            boolean found = false;

            for (int i = 0; i < people.size(); i++) {
                Person person = people.get(i);
                String surname = extractSurname(person.getFullName());
                if (surname.equals(surnameQuery)) {
                    printPersonCondition(i, person);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Люди с такой фамилией не найдены.");
            }
        } else {
            for (int i = 0; i < people.size(); i++) {
                printPersonCondition(i, people.get(i));
            }
        }
    }

    private static void printPersonCondition(int index, Person person) {
        System.out.println("Индекс " + index + ": " + person);
        System.out.println("  Категория физического состояния: " + person.getPhysicalCondition());
    }

    private static String extractSurname(String fullName) {
        String normalized = fullName.trim().toLowerCase();
        int spaceIndex = normalized.indexOf(' ');
        if (spaceIndex == -1) {
            return normalized;
        }
        return normalized.substring(0, spaceIndex);
    }

    private static void sortPeopleByField(Scanner scanner, ArrayList<Person> people) {
        if (people.size() < 2) {
            System.out.println("Для сортировки нужно минимум 2 объекта.");
            return;
        }

        System.out.println("Сортировать по полю:");
        System.out.println("1. ФИО (fullName)");
        System.out.println("2. Возраст (age)");
        System.out.println("3. Пол (gender)");
        System.out.println("4. Национальность (nationality)");
        System.out.println("5. Рост в см (heightCm)");
        System.out.println("6. Вес в кг (weightKg)");
        int field = readIntInRange(scanner, "Выберите поле (1-6): ", 1, 6);

        //  сортировка выбором по возрастанию
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

        System.out.println("Сортировка завершена.");
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

    private static void printIndexList(ArrayList<Person> people) {
        System.out.println("Объекты:");
        for (int i = 0; i < people.size(); i++) {
            System.out.println("[" + i + "] " + people.get(i).getFullName());
        }
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

    private static double readDoubleInRange(Scanner scanner, String prompt, double min, double max) {
        
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
        // Ручной разбор десятичного 
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
}

class Person {
    private String fullName;
    private int age;
    private String gender;
    private String nationality;
    private double heightCm;
    private double weightKg;

    public Person() {
        // Значения "неизвестно" для пустого объекта 
        this.fullName = "неизвестно";
        this.age = -1;
        this.gender = "неизвестно";
        this.nationality = "неизвестно";
        this.heightCm = -1.0;
        this.weightKg = -1.0;
    }

    public Person(String fullName, int age, String gender, String nationality, double heightCm, double weightKg) {
        this();
        // Используем сеттеры
        setFullName(fullName);
        setAge(age);
        setGender(gender);
        setNationality(nationality);
        setHeightCm(heightCm);
        setWeightKg(weightKg);
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public boolean setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        this.fullName = fullName.trim();
        return true;
    }

    public boolean setAge(int age) {
        if (age < 0 || age > 130) {
            return false;
        }
        this.age = age;
        return true;
    }

    public boolean setGender(String gender) {
        if (gender == null) {
            return false;
        }
        String normalized = gender.trim().toLowerCase();
        if (!normalized.equals("мужской") && !normalized.equals("женский")) {
            return false;
        }
        this.gender = normalized;
        return true;
    }

    public boolean setNationality(String nationality) {
        if (nationality == null || nationality.trim().isEmpty()) {
            return false;
        }
        this.nationality = nationality.trim();
        return true;
    }

    public boolean setHeightCm(double heightCm) {
        if (heightCm < 50.0 || heightCm > 250.0) {
            return false;
        }
        this.heightCm = heightCm;
        return true;
    }

    public boolean setWeightKg(double weightKg) {
        if (weightKg < 2.0 || weightKg > 500.0) {
            return false;
        }
        this.weightKg = weightKg;
        return true;
    }

    public String getPhysicalCondition() {
        // Если данных недостаточно, вычисление результата невозможно.
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

    @Override
    public String toString() {
        return "ФИО: " + fullName
                + ", возраст: " + age
                + ", пол: " + gender
                + ", национальность: " + nationality
                + ", рост (см): " + heightCm
                + ", вес (кг): " + weightKg;
    }
}
