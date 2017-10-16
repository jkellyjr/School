/*
 * Project 3: VM Simulator
 * By: John Kelly Jr
 *
 */


public class PageTableEntry{

	int index;
	int frame;
	boolean valid;
	boolean dirty;
	boolean referenced;


	// shell
	public PageTableEntry(){
		index = 0;
		frame = -1;
		valid = false;
		dirty = false;
		referenced = false;
	}


	// duplicate
	public PageTableEntry(PageTableEntry entry){
		index = entry.index;
		frame = entry.frame;
		valid = entry.valid;
		dirty = entry.dirty;
		referenced = entry.referenced;
	}
}
