// консольное приложение “Калькулятор”

import java.util.Scanner;
import java.util.TreeMap;

class Main {
    public static String[] calc(String input) {

        return new String[0];
    }

    public static void main(String[] args) {
        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        Scanner input_data = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение: ");
        String input = input_data.nextLine();
        int actionIndex=-1;                                   // определяем арифметическое выражение
        for (int i = 0; i < actions.length; i++) {
            if (input.contains(actions[i])){
                actionIndex = i;
                break;
            }
        }
        if (actionIndex==-1){
            System.out.println("Вы не верно ввели данные");
            return;
        }
        String[] data = input.split(regexActions[actionIndex]);
        if(converter.isRoman(data[0]) == converter.isRoman(data[1])){    // определяем находятся ли числа в одном формате (арабские или римские)

            int a,b;

            boolean isRoman = converter.isRoman(data[0]);      // определяем, римские ли это числа
            if(isRoman){
                a = converter.romanToInt(data[0]);             // если римские, то конвектируем их в арабские
                b = converter.romanToInt(data[1]);

            }else{
                a = Integer.parseInt(data[0]);                 // если арабские,то конвектируем их из строки в число
                b = Integer.parseInt(data[1]);
            }
            int result;
            switch (actions[actionIndex]){
                case "+":
                    result = a+b;
                    break;
                case "-":
                    result = a-b;
                    break;
                case "*":
                    result = a*b;
                    break;
                default:
                    result = a/b;
                    break;
            }

            if (!(a <= 10 && b <= 10)) {
                System.out.println("Введите арабские числа не более 10");   // вводим числа от 1 до 10
                return;
            }

            if (isRoman){
                System.out.println(converter.intToRoman(result));  // если числа были римские, возвращаем результат в римском числе
            }
            else {
                System.out.println("Результат: " + result);        // если числа были арабские, возвращаем результат в арабском числе
            }
        }
        else {
            System.out.println("Числа должны быть в одном формате (либо арабские, либо римские)");
        }
    }
}



class Converter {
    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    Converter() {
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);

        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");
    }
    boolean isRoman(String number){                         // метод isRoman принимает строку и проверяет ее, что она является ключом нашей Map в типе значений char
        return romanKeyMap.containsKey(number.charAt(0));
    }                                                       // containsKey проверяет среди списков ключей есть ли наш char
    String intToRoman(int number) {                         // конвертация арабского числа в римское
        String roman = "";                                  // строчка, нужна для сохранения результируещего римского числа (число собирается по кусочкам)
        int arabianKey;
        do {
            arabianKey = arabianKeyMap.floorKey(number);    // метод floorKey ищет снизу
            roman += arabianKeyMap.get(arabianKey);
            number -= arabianKey;
        } while (number != 0);
        return roman;
    }

    int romanToInt(String s) {
        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = romanKeyMap.get(arr[end]);
        for (int i = end - 1; i >= 0; i--) {
            arabian = romanKeyMap.get(arr[i]);
            if (arabian < romanKeyMap.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }
}