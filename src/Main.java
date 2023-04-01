import java.util.Scanner;
import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
class Trigonometry implements Serializable {
    double x;
    double y;
    Trigonometry() {
        x = 0;
        y = 0;
    }

    void Designer(double x) {
        this.x = x;
        y = this.x - Math.sin(this.x);
        System.out.println(y);
    }
}

public class Main {

    public static final Logger LOGGER = Logger.getLogger("Trigonometry");

    public static void main(String[] args) {
        String enter;
        Scanner in = new Scanner(System.in); //ввод либо числа, либо команды
        Trigonometry y = new Trigonometry();  //присваивание переменной
        System.out.println((char) 27 + "[44mThe example is given: y = x - sinx" + (char) 27 + "[0m");
        System.out.println((char) 27 + "[36mEnter the number to be stored in the variable (x). [In this case, you will get the answer (or what is the value (y))]" + (char) 27 + "[0m");
        System.out.println((char) 27 + "[44mYou can also:" + (char) 27 + "[0m");

        System.out.println((char) 27 + "[36m♡ SAVE the new value using the \"s\" command;\n" +
                "♡ UPLOAD the value using the \"u\" command;\n" +
                "♡ CHECK the current value using the \"c\" command." + (char) 27 + "[0m");

        FileHandler fh;
        try {
            fh = new FileHandler("log.log"); // создаем файл с логами
            SimpleFormatter formatter = new SimpleFormatter(); // определяем формат логирования
            fh.setFormatter(formatter); // рассказываем файлу про наш формат
            LOGGER.addHandler(fh); // рассказываем логгеру, про то что нужно логировать еще и в файл
            LOGGER.setUseParentHandlers(false); // говорим логгеру, чтобы он ничего не писал в консоль, только в файл
            LOGGER.info("log initialized"); // тестируем
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("program started"); // логируем старт
        while (true) {
            enter = in.nextLine();
            LOGGER.info("input: " + enter); // логируем входные данные
            try {
                LOGGER.info("calculating sin"); // логируем расчет синуса
                double x = Double.parseDouble(enter);
                y.Designer(x);
            } catch (Exception ex) {
                LOGGER.info("parsing command"); // логируем поиск команды
                if (enter.equalsIgnoreCase("s")) {
                    try (ObjectOutputStream object = new ObjectOutputStream(new FileOutputStream("output"))) {
                        object.writeObject(y);
                        System.out.println((char) 27 + "[45mValues saved." + (char) 27 + "[0m");
                        LOGGER.info("values saved"); // логируем сохранение значений
                    } catch (IOException ignored) {
                    }
                } else if (enter.equalsIgnoreCase("u")) {
                    try (ObjectInputStream obj = new ObjectInputStream(new FileInputStream("output"))) {
                        y = (Trigonometry) obj.readObject();
                        System.out.println((char) 27 + "[45mValues restored." + (char) 27 + "[0m");
                        LOGGER.info("values restored"); // логируем восстановление значений
                    } catch (Exception ignored) {
                    }
                } else if (enter.equalsIgnoreCase("c")) {
                    System.out.println((char) 27 + "[45mThe variables correspond to:" + (char) 27 + "[0m");
                    System.out.println("x = " + y.x + " y = " + y.y);
                    LOGGER.info("values checked"); // логируем вывод текущих значений
                } else {
                    System.out.println((char) 27 + "[31mError. Make sure that the values are entered correctly." + (char) 27 + "[0m");
                    LOGGER.info("error: unknown command"); // логируем ошибку с неккоректной командой
                }
            }
        }
    }
}

