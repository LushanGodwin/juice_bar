package lk.ijse.management.regexPattern;

import java.util.regex.Pattern;

public class RegexPatterns {
        private static final Pattern empIdPattern = Pattern.compile("E\\d{3}$");
        private static final Pattern customerIdPattern = Pattern.compile("C\\d{3}$");
        private static final Pattern supplierIdPattern = Pattern.compile("S\\d{3}$");
        private static final Pattern juiceIdPattern = Pattern.compile("J\\d{3}$");
        private static final Pattern foodIdPattern = Pattern.compile("F\\d{3}$");
        private static final Pattern mobilePattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
        private static final Pattern intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");


        public static Pattern getEmpIdPattern() {
            return empIdPattern;
        }
        public static Pattern getJuiceIdPattern() {return juiceIdPattern;}
        public static Pattern getFoodIdPattern() {return foodIdPattern;}

        public static Pattern getCustomerIdPattern() {
            return customerIdPattern;
        }

        public static Pattern getSupplierIdPattern() {
            return supplierIdPattern;
        }


        public static Pattern getMobilePattern() {
            return mobilePattern;
        }

        public static Pattern getIntPattern() {
            return intPattern;
        }

}
