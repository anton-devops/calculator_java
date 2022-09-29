import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, IndexOutOfBoundsException, Exception {
        System.out.println("Input arithmetic expression:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println("Output:");
        System.out.println(calc(input));
    }

    public static String calc(String input) throws IOException, IndexOutOfBoundsException, Exception {

        int MIN = 1;
        int MAX = 10;

        String result;

        int a = 0, b = 0;

        // OPERATORS:
        String PLUS = "+";
        String MINUS = "-";
        String MULTIPLY = "*";
        String DIVIDE = "/";
        String[] OPERATORS = {PLUS, MINUS, MULTIPLY, DIVIDE};

        // prepare input string: remove all spaces:
        String preparedInputString = input.replaceAll("\\s+", "");

        // count all operators:
        int countOfOperators = (
                preparedInputString.split("\\+").length - 1 +
                preparedInputString.split("\\-").length - 1 +
                preparedInputString.split("\\*").length - 1 +
                preparedInputString.split("\\/").length - 1
        );

        if (countOfOperators > 1) {
            throw new IOException(
                    "формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)"
            );
        }

        System.out.println(countOfOperators);

        // find operator in the prepared input string:
        String operator = "";
        for (String item : OPERATORS) {
            if (preparedInputString.contains(item)) {
                operator = item;
                break;
            }
        }

        // get a and b from prepared input string:
        String firstElement = preparedInputString.substring(0, preparedInputString.indexOf(operator));
        String secondElement = preparedInputString.substring(preparedInputString.indexOf(operator) + 1);

        boolean isRomeDigit = false;
        try {
            a = Integer.parseInt(firstElement);
            b = Integer.parseInt(secondElement);
        } catch (Exception e) {
            try {
                a = RomeDigit.valueOf(firstElement).getValue();
                b = RomeDigit.valueOf(secondElement).getValue();
                isRomeDigit = true;
            } catch (Exception exceptionRomeDigits) {
                throw new IOException("строка не является математической операцией");
            }
        }

        if (!(MIN <= a && a <= MAX) || !(MIN <= b && b <= MAX)) {
            throw new IndexOutOfBoundsException("a и b должны принадлежать диапазону чисел от " + MIN + " до " + MAX);
        }

        if (isRomeDigit && a <= b && operator == MINUS) {
            throw new IndexOutOfBoundsException("в римской системе нет отрицательных чисел");
        }

        switch (operator) {
            case "+":
                result = (isRomeDigit) ? RomeDigit.toString(a + b) : "" + (a + b);
                break;
            case "-":
                result = (isRomeDigit) ? RomeDigit.toString(a - b) : "" + (a - b);
                break;
            case "*":
                result = (isRomeDigit) ? RomeDigit.toString(a * b) : "" + (a * b);
                break;
            case "/":
                result = (isRomeDigit) ? RomeDigit.toString(a / b) : "" + (a / b);
                break;
            default:
                throw new IOException("ERROR: Wrong operator.");
        }
        return result;
    }
}
