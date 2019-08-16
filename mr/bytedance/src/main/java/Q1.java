import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int a1[] = new int[n];

        for (int i = 0; i < n; i++) {
            int hh = scanner.nextInt();

            int mm = scanner.nextInt();
            if(hh == 0 ) {
                hh = 24;
            }
            a1[i] = hh * 60 + mm;
        }

        dsort(a1);

        int x = scanner.nextInt();
        int a = scanner.nextInt();
        if(a == 0) {
            a = 24;
        }
        int b = scanner.nextInt();

        int sm = a * 60 + b - x;
        for (int i = 0; i < n; i++) {
            if (a1[i] <= sm) {
                int h = sm / 60;
                int m = sm % 60;
                System.out.printf("%d %d", h, m);
                break;

            }
        }

    }


    public static void dsort(int array[]) {
        int n = array.length;
        boolean flag;
        int t;
        for (int i = 0; i < n - 1; i++) {
            flag = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] < array[j + 1]) {
                    flag = true;
                    t = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = t;
                }
            }
            if (!flag) {
                break;
            }
        }
    }
}
