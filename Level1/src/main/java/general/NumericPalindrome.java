package general;


public class NumericPalindrome {
    
    public static void main(String[] args) {
        NumericPalindrome t = new NumericPalindrome();
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(303));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(3003));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(31113));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(3113));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(313));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(101));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(10));
        System.out.println("Is Numeric Palendrome: " + t.isNumericPalendrome(1));
    }
    
    private boolean isNumericPalendrome(int k) {
        long counter = 1;
        double l = 0, r = 0;
        int t = 0;
        
        while((t = t/10) > 0)
            counter++;
        
        for(int i=0; i<counter; i++) {
            int d = k%10;
            k = k/10;
            
            if(i == counter/2 && counter%2==1)
                continue;
            if(d == 0)
                continue;
            
            if(i >= counter/2) {
                l = l + (d * (Math.pow(10, counter-i-1)));
            } else {
                r = r + (d * (Math.pow(10, i)));
            }
        }
        
        return (l == r);
    }
}
