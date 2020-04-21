import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class customeCode {
    //DO NOT change the next line except for renaming input variables
    public double customFunc(float[] c1, float[] c2) {
        /**
         * Please write your code here
         * c1 and c2 are arrays of pixel values of
         * fluorescence channels in the same cell
         * Here is an example of how to calculate
         * Pearson's correlation coefficient */

        float[] c = c1.clone();
        for (int i = 0; i < c.length; i++) {
            c[i] *= c2[i];
        }
        return (getMean(c) -
                getMean(c1) * getMean(c2)) /
                (getSTD(c1) * getSTD(c2));
    }

    // = x0 + x1  + ... + xn / n
    private double getMean(float[] x) {
        double result = 0.0;
        for (int i = 0; i < x.length; i++)
            result += x[i];
        return result / x.length;
    }

    // = sqrt (x0*x0 + x1* x1 + ... + xn*xn - (x0 + x1  + ... + xn / n) ^2 )
    private double getSTD(float[] x) {
        float[] y = x.clone();
        for (int i = 0; i < x.length; i++)
            y[i] *= y[i];
        return java.lang.Math.sqrt(
                getMean(y) - getMean(x) * getMean(x));
    }


    public float minValue(float[] arr) {
        float result = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] < result) {
                result = arr[i];
            }
        }
        return result;
    }

    public float maxValue(float[] arr) {
        float result = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] > result) {
                result = arr[i];
            }
        }
        return result;
    }

    public float midValue(float[] arr) {
        // sort arr
        List<Float> arrList = new ArrayList<>();
        for(int i =0; i < arr.length; ++i) {
            arrList.add(arr[i]);
        }
        Collections.sort(arrList);
        return arrList.get(arrList.size()/2);
    }


//    // 测试
//    public static void main(String[] args) {
//        customeCode custome = new customeCode();
//        float[] arr = {1.0f, 2.1f, 3.2f, 4.3f, 3.5f, 2.6f, 1.1f, 0.4f, 2.9f};
//        System.out.println("最小=" + custome.minValue(arr));
//        System.out.println("最大=" + custome.maxValue(arr));
//        System.out.println("中位数=" + custome.midValue(arr));
//    }
}
