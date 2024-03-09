import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CalculatorException {
/*        Создаем ввод пользователем калькулируемой строки и преобразуем введенные
        данные в строку String input:
*/
        Scanner scanner = new Scanner(System.in);
        String[] arifmeticSigns = {"\\-", "\\/", "\\+", "\\*"};  // массив, состоящий из возможных арифметических действий калькулятора (к каждому из операторов добавлен двойной обратный слеш \\, что бы не вызвать логический сбой программы.
        String sign = "No";   // переменная, соответствующая оператору (арифметическому действию)
        String first = null;  // переменная,соответствующая левой от оператора(арифметического знака) части входящей строки
        String second = null; // переменная,соответствующая правой от оператора(арифметического знака) части входящей строки
        int x = 0;   // переменная, соответствующая числовому выражению переменной first
        int y = 0;   // переменная, соответствующая числовому выражению переменной second
        int result = 0; // переменная, используемая для обработки арифметического действия с римскими цифрами
        try {
            System.out.println("Введите строку калькуляции");
            String input = scanner.nextLine();
            for (String s : arifmeticSigns) {
                String[] string = input.split(s);
                if (string.length == 2) {
                    sign = s;
                    first = string[0].trim();
                    second = string[1].trim();
                    for (String s1 : arifmeticSigns) {
                        if((" " + first + " ").split(s1).length > 1 || (" " + second + " ").split(s1).length > 1 ){
                            throw new CalculatorException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                        }
                    }
                    break;
                } else if (string.length > 2) {
                    throw new CalculatorException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                }
            }
            if (sign.equals("No")) throw new CalculatorException("Cтрока не является математической операцией");
            /* Проверяем что из себя представляют переменные first и second.Если это арабские числа, то
            Исключение NumberFormatException не сработает и программа продолжит выполняться дальше,
            в текущем блоке try{}. Если же какая то из этих переменных не число, то сработает исключение и
            дальнейшее выполнение программы будет продолжено б блоке обработки этого исключения
            catch (NumberFormatException e){}
             */
            x = Integer.parseInt(first);
            y = Integer.parseInt(second);
            if (x > 10 || y > 10 || x < 1 || y < 1) {
                throw new CalculatorException("Не соблюдено условие : оба числа должны находиться в интервале от 1 до 10 включительно");
            }
            switch (sign) {
                case "\\-":
                    System.out.println(x - y);
                    break;
                case "\\+":
                    System.out.println(x + y);
                    break;
                case "\\*":
                    System.out.println(x * y);
                    break;
                case "\\/":
                    System.out.println(x / y);
                    break;
                default:
                    System.out.println("Логическая ошибка в коде, арифметический знак не определен");

            }
        } catch (NumberFormatException e) {
            RomanNumeral[] romanNumerals = RomanNumeral.values();
            if (x != 0) {
                for (RomanNumeral romanNumeral : romanNumerals) {
                    if (second.equals(romanNumeral.toString())) y = romanNumeral.getArabiсNumber();
                }
                if (y > 0) {
                    throw new CalculatorException("используются одновременно разные системы счисления");
                } else if (y == 0 && ( x > 1 && x<=10 )) {
                    throw new CalculatorException("Второе число не удовлетворяет условию задания");
                }else if(y == 0) {
                    throw new CalculatorException("Оба числа не удовлетворяют условию задания");
                }
            }
            for (RomanNumeral romanNumeral : romanNumerals) {
                if (first.equals(romanNumeral.toString())) x = romanNumeral.getArabiсNumber();
                if (second.equals(romanNumeral.toString())) y = romanNumeral.getArabiсNumber();
            }
            try {
                if (x > 0 && y == 0){
                    Integer.parseInt(second);
                    throw new CalculatorException("используются одновременно разные системы счисления");
                }
            } catch (NumberFormatException ex) {
                throw new CalculatorException("Второе число не удовлетворяет условию задания");
            }
            if (x == 0) {
                throw new CalculatorException("Первое число не удовлетворяет условию задания");
            }
            if (y == 0) {
                throw new CalculatorException("Второе число не удовлетворяет условию задания");
            }
            switch (sign) {
                case "\\-":
                    result = x - y;
                    break;
                case "\\+":
                    result = x + y;
                    break;
                case "\\*":
                    result = x * y;
                    break;
                case "\\/":
                    result = x / y;
                    break;
            }
            if(result < 1){
                throw new CalculatorException("В римской системе нет отрицательных чисел или нуля");
            }
            if(result >= 1){
                int[] massiv = {100,90,50,40,10,9,5,4,1};
                for (Integer del : massiv){
                   while (result/del >= 1){
                       switch (del){
                           case 100 : System.out.print("C"); break;
                           case 90 : System.out.print("XC"); break;
                           case 50 : System.out.print("L"); break;
                           case 40 : System.out.print("XL"); break;
                           case 10 : System.out.print("X"); break;
                           case 9 : System.out.print("IX"); break;
                           case 5 : System.out.print("V"); break;
                           case 4 : System.out.print("IV"); break;
                           case 1 : System.out.print("I"); break;
                       }
                       result = result - del;
                   }
                   if (result == 0) break;
                }
            }
        }
        finally{
        scanner.close(); // закрываем scanner в блоке finally{}
    }
    }

}





