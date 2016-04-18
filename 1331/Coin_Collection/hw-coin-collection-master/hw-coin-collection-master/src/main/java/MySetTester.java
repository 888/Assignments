import java.util.Arrays;
/**
 * Tester to test MySet
 * @author achiang31
 */
public class MySetTester {

    /**
     * Main method to test all methods in MySet
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        /*
         * Test MySet
         */
        MySet<String> set = new MySet<>();

        System.out.println("Testing add, contains:");

        // test add and contains
        set.add("That");
        set.add("aint");
        set.add("Falco!");
        set.add("Falco!");
        set.add("That");
        System.out.printf("Your set has %d elements, and \"Falco!\" %s"
                        + " in the set.  PASS? %b\n",
                set.size(),
                set.contains("Falco!") ? "is" : "is not",
                set.size() == 3 && set.contains("Falco!"));


        // test remove
        System.out.println("Testing remove:");
        set.remove("Falco!");
        System.out.printf("Your set has %d elements, and \"Falco!\" %s"
                        + " in the set.  PASS? %b\n",
                set.size(),
                set.contains("Falco!") ? "is" : "is not",
                set.size() == 2 && !set.contains("Falco!"));

        // test addAll
        System.out.println("Testing addAll:");
        set.addAll(Arrays.asList("Happy", "Feet"));
        System.out.printf("Your set has %d elements, and \"Feet\" %s"
                        + " in the set.  PASS? %b\n",
                set.size(),
                set.contains("Feet") ? "is" : "is not",
                set.size() == 4 && set.contains("Feet"));

        // test removeAll
        System.out.println("Testing removeAll:");
        set.removeAll(Arrays.asList("Happy", "That"));
        System.out.printf("Your set has %d elements, and \"That\" %s"
                        + " in the set.  PASS? %b\n",
                set.size(),
                set.contains("That") ? "is" : "is not",
                set.size() == 2 && !set.contains("That"));

        //testing clear
        System.out.println("Testing clear:");
        System.out.println(set.size());
        set.clear();
        System.out.println(set.size());
        System.out.println((!(set.isEmpty())) ? "false" : "true");


        //testing containsAll
        System.out.println("Testing containsAll:");
        System.out.println(set.size());
        System.out.println(set.addAll(Arrays.asList("Yhwach", "Haschwalth")));
        System.out.println(set.containsAll(
                Arrays.asList("Yhwach", "Haschwalth")));
        System.out.println(set.contains("Yhwach"));
        System.out.println(set.contains("Haschwalth"));
        System.out.println(set.size());

        //testing equals
        System.out.println("Testing equals:");
        MySet<String> seteq = new MySet<>();
        seteq.addAll(set);
        System.out.println(!(set.equals(null)));
        System.out.println(set.equals(set));
        System.out.println(set.equals(seteq));

        //testing isEmpty
        System.out.println("Testing isEmpty:");
        set.clear();
        System.out.println((!(set.isEmpty())) ? "false" : "true");

        //testing retainAll
        System.out.println("Testing retainAll:");
        set.addAll(seteq);
        seteq.remove("Yhwach");
        set.retainAll(seteq);
        System.out.println(set.size());
        System.out.println(!(set.contains("Yhwach")));
        System.out.println(set.contains("Haschwalth"));

        //testing size
        System.out.println("Testing size:");
        int count = 0;
        for (String element : set) {
            count++;
        }
        System.out.println(set.size());
        System.out.println(count);
        System.out.println(set.size() == count);

        //testing toArray
        System.out.println("Testing toArray:");
        Object[] a = set.toArray();
        Object[] b = seteq.toArray();
        Object[] c = set.getBackingArray();
        Object[] d = seteq.getBackingArray();
        System.out.println(a.length);
        System.out.println(c.length);
        System.out.println(b.length);
        System.out.println(d.length);
        System.out.println(a[0]);
        System.out.println(b[0]);
        for (Object filler : c) {
            if (filler != null) {
                System.out.println(filler);
            }
        }
        for (Object filler : d) {
            if (filler != null) {
                System.out.println(filler);
            }
        }
    }
}