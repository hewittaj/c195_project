package Password;

import java.util.Scanner;

public class Password {
    public static String getPassword(){
        // My db password is 53689048180
        Scanner s = new Scanner(System.in);
        System.out.println("Password: ");
        String password = s.next();
        return password;
    }


}
