import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CustomArrayListTest {
	
    private ArrayListInterface<Character> charList;

    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
    	charList = new ArrayList<Character>();
    }


@Test(timeout = TIMEOUT)
public void testAlphabet() {
	assertEquals(0, charList.size());
	
	//Add low alphabet
	for (int i = 0; i < 26; i++) {
		charList.addToBack((char) ('a' + i));
	}
	
	Object[] fullLowAlpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	//Print array
	for (int i = 0; i < charList.size(); i++) {
		System.out.print(charList.getBackingArray()[i] + " ");
	}
	System.out.println();
	
	//Size check
	assertEquals(26, charList.size());
	
	//Vowel check
	assertEquals(charList.getBackingArray()[0], 'a');
	assertEquals(charList.getBackingArray()[4], 'e');
	assertEquals(charList.getBackingArray()[8], 'i');
	assertEquals(charList.getBackingArray()[14], 'o');
	assertEquals(charList.getBackingArray()[20], 'u');
	
	//Check all
	for (int i = 0; i < fullLowAlpha.length; i++) {
		assertEquals(fullLowAlpha[i], charList.getBackingArray()[i]);
	}
	
	//Remove the ends
	charList.removeFromFront();
	charList.removeFromBack();
	
	//Print array
	for (int i = 0; i < charList.size(); i++) {
		System.out.print(charList.getBackingArray()[i] + " ");
	}
	System.out.println();
	
	//Size Check
	assertEquals(24, charList.size());
	//Null check
	assertEquals(null, charList.getBackingArray()[25]);
	assertEquals(null, charList.getBackingArray()[26]);
	//Vowel check
	assertEquals(charList.getBackingArray()[3], 'e');
	assertEquals(charList.getBackingArray()[7], 'i');
	assertEquals(charList.getBackingArray()[13], 'o');
	assertEquals(charList.getBackingArray()[19], 'u');

	//Add the ends back
	charList.addToFront('a');
	charList.addToBack('z');
	
	//Print array
	for (int i = 0; i < charList.size(); i++) {
		System.out.print(charList.getBackingArray()[i] + " ");
	}
	System.out.println();
	
	//Size Check
	assertEquals(26, charList.size());
	//Check all
	for (int i = 0; i < fullLowAlpha.length; i++) {
		assertEquals(fullLowAlpha[i], charList.getBackingArray()[i]);
	}
	
	Object[] halfLowAlpha = {'a', 'c', 'e', 'g', 'i', 'k', 'm', 'o', 'q', 's', 'u', 'w', 'y',};
	
	//Remove odd indices
	for (int i = 1; i < charList.size(); i++) {
		charList.removeAtIndex(i);
	}
	
	//Print array
	for (int i = 0; i < charList.size(); i++) {
		System.out.print(charList.getBackingArray()[i] + " ");
	}
	System.out.println();
	
	//Check all
	for (int i = 0; i < halfLowAlpha.length; i++) {
		assertEquals(halfLowAlpha[i], charList.getBackingArray()[i]);
	}
	
	//Null check
	for (int i = halfLowAlpha.length; i < fullLowAlpha.length; i++) {
		assertEquals(null, charList.getBackingArray()[i]);
	}
	
	
	Object[] fullLowAndCapAlpha = {'a', 'B', 'c', 'D', 'e', 'F', 'g', 'H', 'i', 'J', 'k', 'L', 'm',
			'N', 'o', 'P', 'q', 'R', 's', 'T', 'u', 'V', 'w', 'X', 'y', 'Z'};
	
	//Add caps at odd indices
	for (int i = 1; i < fullLowAndCapAlpha.length; i+=2) {
		charList.addAtIndex(i, (char)('A' + i));
	}
	
	//Print array
	for (int i = 0; i < charList.size(); i++) {
		System.out.print(charList.getBackingArray()[i] + " ");
	}
	System.out.println();
	
	//Check all
	for (int i = 0; i < fullLowAndCapAlpha.length; i++) {
		assertEquals(fullLowAndCapAlpha[i], charList.getBackingArray()[i]);
	}
	
	charList.clear();
	
	//Check all
	for (int i = 0; i < fullLowAlpha.length; i++) {
		assertEquals(null, charList.getBackingArray()[i]);
	}
	
	assertEquals(true, charList.isEmpty());
	
	}

}
