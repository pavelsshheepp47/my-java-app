// Person.java
package two;

/**
 * Представляет человека с личными и физическими характеристиками.
 *
 * <p>
 * Поля проверяются при установке. Недопустимые значения отклоняются, поле остаётся без изменений.
 */
public final class Person {

  private static final String UNKNOWN = "неизвестно";
  private static final int MIN_AGE = 0;
  private static final int MAX_AGE = 130;
  private static final double MIN_HEIGHT_CM = 50.0;
  private static final double MAX_HEIGHT_CM = 250.0;
  private static final double MIN_WEIGHT_KG = 2.0;
  private static final double MAX_WEIGHT_KG = 500.0;

  private String fullName;
  private int age;
  private String gender;
  private String nationality;
  private double heightCm;
  private double weightKg;

  /** Конструктор по умолчанию с заполнителями. */
  public Person() {
    this.fullName = UNKNOWN;
    this.age = -1;
    this.gender = UNKNOWN;
    this.nationality = UNKNOWN;
    this.heightCm = -1.0;
    this.weightKg = -1.0;
  }

  /**
   * Создаёт человека с указанными атрибутами.
   *
   * @param fullName полное имя; не должно быть null или пустым
   * @param age возраст в годах; должен быть от 0 до 130
   * @param gender пол; должно быть "мужской" или "женский"
   * @param nationality национальность; не должна быть null или пустой
   * @param heightCm рост в сантиметрах; должен быть от 50.0 до 250.0
   * @param weightKg вес в килограммах; должен быть от 2.0 до 500.0
   */
  public Person(final String fullName, final int age, final String gender, final String nationality,
      final double heightCm, final double weightKg) {
    this();
    setFullName(fullName);
    setAge(age);
    setGender(gender);
    setNationality(nationality);
    setHeightCm(heightCm);
    setWeightKg(weightKg);
  }

  // Геттеры

  public String getFullName() {
    return fullName;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public String getNationality() {
    return nationality;
  }

  public double getHeightCm() {
    return heightCm;
  }

  public double getWeightKg() {
    return weightKg;
  }

  // Сеттеры (возвращают boolean для указания успеха)

  /**
   * Устанавливает полное имя.
   *
   * @param fullName полное имя; не должно быть null или пустым
   * @return {@code true}, если успешно установлено, {@code false} иначе
   */
  public boolean setFullName(final String fullName) {
    if (fullName == null || fullName.trim().isEmpty()) {
      return false;
    }
    this.fullName = fullName.trim();
    return true;
  }

  public boolean setAge(final int age) {
    if (age < MIN_AGE || age > MAX_AGE) {
      return false;
    }
    this.age = age;
    return true;
  }

  public boolean setGender(final String gender) {
    if (gender == null) {
      return false;
    }
    String normalized = gender.trim().toLowerCase();
    if (!"мужской".equals(normalized) && !"женский".equals(normalized)) {
      return false;
    }
    this.gender = normalized;
    return true;
  }

  public boolean setNationality(final String nationality) {
    if (nationality == null || nationality.trim().isEmpty()) {
      return false;
    }
    this.nationality = nationality.trim();
    return true;
  }

  public boolean setHeightCm(final double heightCm) {
    if (heightCm < MIN_HEIGHT_CM || heightCm > MAX_HEIGHT_CM) {
      return false;
    }
    this.heightCm = heightCm;
    return true;
  }

  public boolean setWeightKg(final double weightKg) {
    if (weightKg < MIN_WEIGHT_KG || weightKg > MAX_WEIGHT_KG) {
      return false;
    }
    this.weightKg = weightKg;
    return true;
  }
}
