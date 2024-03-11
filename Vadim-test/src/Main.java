import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CalculatorException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку калькуляции");
        String input = scanner.nextLine();
        System.out.println(calc(input));
        scanner.close();
    }

    public static String calc(String input) throws CalculatorException {
        String itogo = "";
        String[] arifmeticSigns = {"\\-", "\\/", "\\+", "\\*"};  // массив, состоящий из возможных арифметических действий калькулятора (к каждому из операторов добавлен двойной обратный слеш \\, что бы не вызвать логический сбой программы.
        String sign = "No";   // переменная, соответствующая оператору (арифметическому действию)
        String first = null;  // переменная,соответствующая левой от оператора(арифметического знака) части входящей строки
        String second = null; // переменная,соответствующая правой от оператора(арифметического знака) части входящей строки
        int x = 0;   // переменная, соответствующая числовому выражению переменной first (первому числу)
        int y = 0;   // переменная, соответствующая числовому выражению переменной second (второму числу)
        int result = 0; // переменная, используемая для обработки арифметического действия с римскими цифрами
        for (String s : arifmeticSigns) {
            String[] string = input.split(s);
            if (string.length == 2) {
                sign = s;
                first = string[0].trim();
                second = string[1].trim();
                for (String s1 : arifmeticSigns) {
                    if ((" " + first + " ").split(s1).length > 1 || (" " + second + " ").split(s1).length > 1) {
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
        try {
            x = Integer.parseInt(first);
            y = Integer.parseInt(second);
            if( x > 0 && x < 11 && y > 0 && y < 11) {
                switch (sign) {
                    case "\\-":
                        itogo = Integer.toString(x - y);
                        break;
                    case "\\+":
                        itogo = Integer.toString(x + y);
                        break;
                    case "\\*":
                        itogo = Integer.toString(x * y);
                        break;
                    case "\\/":
                        itogo = Integer.toString(x / y);
                        break;
                }
            } else if ((x < 1 || x > 10) && (y < 1 || y > 10)){
                throw new CalculatorException(" Оба числа не находятся в интервале от 1 до 10 включительно");
            }else if (x < 1 || x > 10){
                throw new CalculatorException(" Первое число не находятся в интервале от 1 до 10 включительно");
            }else if (y < 1 || y > 10){
                throw new CalculatorException(" Второе число не находятся в интервале от 1 до 10 включительно");
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
                    if (Integer.parseInt(second) > 0 && Integer.parseInt(second) < 11)
                    throw new CalculatorException("используются одновременно разные системы счисления");
                    else throw new CalculatorException("Используются одновременно разные системы исчисления и второе число не удовлетворяет условию задания");
                }
            } catch (NumberFormatException ex) {
                throw new CalculatorException("Второе число не удовлетворяет условию задания");
            }
            if (x == 0 && y == 0){
                throw new CalculatorException("Оба числа не удовлетворяют условию задания");
            }
            if (x == 0) {
                throw new CalculatorException("Первое число не удовлетворяет условию задания");
            }
            if (y == 0) {
                throw new CalculatorException("Второе число не удовлетворяет условию задания");
            }
            if(result < 1){
                throw new CalculatorException("В римской системе нет отрицательных чисел или нуля");
            }
            if(result >= 1){
                int[] massiv = {100,90,50,40,10,9,5,4,1};
                for (Integer del : massiv){
                    while (result/del >= 1){
                        switch (del){
                            case 100 : itogo = itogo + "C"; break;
                            case 90 : itogo = itogo + "XC"; break;
                            case 50 : itogo = itogo + "L"; break;
                            case 40 : itogo = itogo + "XL"; break;
                            case 10 : itogo = itogo + "X"; break;
                            case 9 : itogo = itogo + "IX"; break;
                            case 5 : itogo = itogo + "V"; break;
                            case 4 : itogo = itogo + "IV"; break;
                            case 1 : itogo = itogo + "I"; break;
                        }
                        result = result - del;
                    }
                    if (result == 0) break;
                }
            }
        }
        return itogo;
    }
}





