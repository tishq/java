/**
 * @Author: 孟红全
 * @Date: 2019/8/11 上午11:32
 * @Version 1.0
 */
public class DoubleSort {
    public static void main(String[] args) {
        int array[] = {4,3,2,1};
        dsort(array);
    }

    public  static void dsort(int a[]) {
        int array[] = a;
        int n=array.length;
        boolean flag;
        int t;
        for(int i = 0; i<n-1;i++) {
            flag = false;
            for (int j=0;j<n-1-i;j++) {
                if(array[j]>array[j+1]) {
                    flag = true;
                    t = array[j];
                    array[j] = array[j+1];
                    array[j+1] = t;
                }
            }
            if(!flag) {
                break;
            }
        }
        for (int i=0;i<n;i++) {
            System.out.println(array[i]);
        }
    }

}
