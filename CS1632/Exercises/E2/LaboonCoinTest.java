import static org.junit.Assert.*;

import org.junit.*;

public class LaboonCoinTest {

    // Re-usable LaboonCoin reference.
    LaboonCoin _l;

    // Create a new LaboonCoin instance before each test.
    @Before
    public void setup() {
    	_l = new LaboonCoin();
    }

    // Assert that creating a new LaboonCoin instance
    // does not return a null reference
    @Test
    public void testLaboonCoinExists() {
    	assertNotNull(_l);
    }

    // Creating a block String with valid data should return
    // a valid block.  This includes printing data out
    // normally, the previous hash and current hash as a hex
    // representations of themself, and the nonce in hex
    // repretentation.  Values should be delimited by
    // pipes.
    @Test
    public void testCreateBlockValid() {
    	int prevHash = 0x0;
    	int nonce = 0x16f2;
    	int hash = 0x545ac;
    	String validBlock = _l.createBlock("kitten", prevHash, nonce, hash);
    	assertEquals("kitten|00000000|000016f2|000545ac", validBlock);
    }

    // Trying to represent a blockchain which has no blocks
    // in it should just return an empty string.
    @Test
    public void testGetBlockChainNoElements() {
    	// By default, _l.blockchain has no elements in it.
    	// So we can just test it immediately.
    	String blockChain = _l.getBlockChain();
    	assertEquals("", blockChain);
    }


    // Viewing the blockchain as a full string which has valid
    // elements should include all of the elements.  Note that the
    // final element DOES have a trailing \n!
    @Test
    public void testGetBlockChainElements() {
    	_l.blockchain.add("TESTBLOCK1|00000000|000010e9|000a3cd8");
    	_l.blockchain.add("TESTBLOCK2|000a3cd8|00002ca8|0008ff30");
    	_l.blockchain.add("TESTBLOCK3|0008ff30|00002171|0009f908");
    	String blockChain = _l.getBlockChain();
    	assertEquals("TESTBLOCK1|00000000|000010e9|000a3cd8\nTESTBLOCK2|000a3cd8|00002ca8|0008ff30\nTESTBLOCK3|0008ff30|00002171|0009f908\n", blockChain);
    }

    // TODO - PUT YOUR SIX TESTS HERE

    // John Kelly
    // Happy Path
    @Test
    public void testHashHappyPath() {
        int val = _l.hash("bill");
        assertEquals(0x53c4142cL, val);
    }
    // Null
    @Test
    public void testHashNull() {
        int val = _l.hash("");
        assertEquals(0x00989680L, val);
    }
    // Collision
    @Test
    public void testHashCollision() {
        int val1 = _l.hash("laboon");
        int val2 = _l.hash("laboon");
        assertEquals(val1, val2);
    }

    /*
      Nathan Davidson -- Tests for Valid Hash
    */
    @Test
    public void testZeroDifficultyValid() {
      boolean valid = _l.validHash(0, 150);
      assertEquals(true, valid);
    }

    @Test
    public void testNineDifficultyInvalid() {
      boolean valid = _l.validHash(9, 0);
      assertEquals(false, valid);
    }

    @Test
    public void smallerNumberZeros() { //Compared to difficulty
      boolean valid = _l.validHash(5, Integer.valueOf("aaaa", 16));
      assertEquals(false, valid);
    }

    @Test
    public void exactNumberZeros() { //Compared to difficulty
      boolean valid = _l.validHash(4, Integer.valueOf("aaaa", 16));
      assertEquals(true, valid);
    }

    @Test
    public void largerNumberZeros() { //Compared to difficulty
      boolean valid = _l.validHash(3, Integer.valueOf("aaaa", 16));
      assertEquals(true, valid);
    }

}
