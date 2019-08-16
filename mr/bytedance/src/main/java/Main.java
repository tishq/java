import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int sum = 100;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        sum = sum*n;
        int d[] = new int[n];
        int m[] = new int[n];
        for(int i = 0;i<n;i++) {
            d[i] = scanner.nextInt();
            m[i]=0;
        }
        for(int i = 1;i<n;i++) {
            if(d[i]>d[i-1]) {
                m[i]+=m[i-1]+100;
            }
        }
        for(int i =0;i<n;i++)  {
            sum +=m[i];
        }
                System.out.println(sum);
                }
                }