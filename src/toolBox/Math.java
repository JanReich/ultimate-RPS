package toolBox;

    public class Math {

        public static int generatePrime(int index) {

            int j = index;
            boolean prime = false;

            while (!prime) {

                for(int i = 2; i < j - 1; i++) {

                    if(j % i == 0) {

                        prime = false;
                        break;
                    } else prime = true;
                }

                if(prime) return j;
                else j++;
            }
            return -1;
        }
    }
