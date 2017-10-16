/*
 * Project 3: VM Simulator
 * By: John Kelly Jr
 *
 */


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Hashtable;


public class Simulator{


	// shell
	public Simulator(){

	}


	// optimal page replacement
	public void opt(String traceFile, int numFrames) throws FileNotFoundException{
		int numAccesses = 0;
		int numPageFaults = 0;
		int numDiskWrites = 0;
		int[] frames = new int[numFrames];


		Hashtable<Integer, PageTableEntry> pageTable = new Hashtable<Integer, PageTableEntry>();
		Hashtable<Integer, LinkedList<Integer>> future = new Hashtable<Integer, LinkedList<Integer>>();
		Scanner sc = new Scanner(new File(traceFile));


		// initialize tables
		for(int i = 0; i < 1024 * 1024; i ++){
			PageTableEntry pte = new PageTableEntry();
			pageTable.put(i, pte);
			future.put(i, new LinkedList<Integer>());
		}


		// initialize frame array
		for(int i = 0; i < numFrames; i++){
			frames[i] = -1;
		}


		// build hashtable
		int lineNum = 0;
		while(sc.hasNext()){
			String [] line = sc.nextLine().split(" ");
			int pageNum = Integer.decode("0x" + line[0].substring(0,5));
			future.get(pageNum).add(lineNum);
			lineNum++;
		}


		// execture optimal page replacement
		int currFrame = 0;
		sc = new Scanner(new File(traceFile));

		while(sc.hasNext()){
			String [] line = sc.nextLine().split(" ");
			int pageNum = Integer.decode("0x" + line[0].substring(0,5));
			future.get(pageNum).removeFirst();

			PageTableEntry pte = pageTable.get(pageNum);
			pte.index = pageNum;
			pte.referenced = true;

			// flip
			if(line[1].equals("W")){
				pte.dirty = true;
			}

			if(pte.valid){
				System.out.println(line[0] + " -- hit");
			} else {
				numPageFaults++;

				// no eviction
				if(currFrame < numFrames){
					frames[currFrame] = pageNum;
					pte.frame = currFrame;
					pte.valid = true;
					currFrame++;

					System.out.println(line[0] + " page fault -- no eviction");
				}
				// evicting
				else {
					int maxDistance = getLongestDistance(frames, future);
					PageTableEntry tempPTE = pageTable.get(maxDistance);

					if(pte.dirty){
						System.out.println(line[0] + " page fault -- evict dirty");
						numDiskWrites++;

					} else {
						System.out.println(line[0] + " page fault -- evict clean");
					}

					// swapping
					pte.frame = tempPTE.frame;
					frames[tempPTE.frame] = pte.index;
					pte.valid = true;
					tempPTE.valid = false;
					tempPTE.dirty = false;
					tempPTE.referenced = false;
					tempPTE.frame = -1;
					pageTable.put(maxDistance, tempPTE);
				}

				pageTable.put(pageNum, pte);
				numAccesses++;
			}
		}

		// display statistics
		printInfo("OPT", numFrames, numAccesses, numPageFaults, numDiskWrites);
	}


	// returns the page with the longest distance from current
	private static int getLongestDistance(int [] frames, Hashtable<Integer, LinkedList<Integer>> future){
		int index = 0;
		int maxDistance = 0;

		for(int i = 0; i < frames.length; i++){

			if(future.get(frames[i]).isEmpty()){
				return frames[i];
			}
			else if(future.get(frames[i]).get(0) > maxDistance){
				maxDistance = future.get(frames[i]).get(0);
				index = frames[i];
			}
		}
		return index;
	}


	// clock
	public void clock(String traceFile, int numFrames) throws FileNotFoundException{
		int numAccesses = 0;
		int numPageFaults = 0;
		int numDiskWrites = 0;
		int[] frames = new int[numFrames];
		int clockPosition = 0;


		Hashtable<Integer, PageTableEntry> pageTable = new Hashtable<Integer, PageTableEntry>();
		Scanner sc = new Scanner(new File(traceFile));


		for(int i = 0; i < 1024*1024; i++){
			PageTableEntry tempPTE = new PageTableEntry();
			pageTable.put(i, tempPTE);
		}


		for(int i = 0; i < numFrames; i++){
			frames[i] = -1;
		}


		int currFrame = 0;
		while(sc.hasNext()){
			String [] line = sc.nextLine().split(" ");
			int pageNum = Integer.decode("0x" + line[0].substring(0,5));


			PageTableEntry pte = pageTable.get(pageNum);
			pte.index = pageNum;
			pte.referenced = true;


			if(line[1].equals("W")){
				pte.dirty = true;
			}


			if(pte.valid){
				System.out.println(line[0] + " hit");
			} else {
				numPageFaults++;

				// no eviction
				if(currFrame < numFrames){
					frames[currFrame] = pageNum;
					pte.frame = currFrame;
					pte.valid = true;
					currFrame++;

					System.out.println(line[0] + " page fault -- no eviction");
				}
				// evicting
				else{
					int pageToEvict = 0;
					boolean foundFlag = false;

					while(!foundFlag){

						if(clockPosition == numFrames){
							clockPosition = 0;
						}

						if(!pageTable.get(frames[clockPosition]).referenced){
							pageToEvict = frames[clockPosition];
							foundFlag = true;
						} else {
							pageTable.get(frames[clockPosition]).referenced = false;
						}
						clockPosition++;
					}

					PageTableEntry tempPTE = pageTable.get(pageToEvict);

					if(pte.dirty){
						System.out.println(line[0] + " page fault -- evict dirty");

						numDiskWrites++;
					} else {
						System.out.println(line[0] + " page fault -- evict clean");
					}


					// swapping
					pte.frame = tempPTE.frame;
					frames[tempPTE.frame] = pte.index;
					pte.valid = true;
					tempPTE.valid = false;
					tempPTE.dirty = false;
					tempPTE.referenced = false;
					tempPTE.frame = -1;

					pageTable.put(pageToEvict, tempPTE);
				}
			}

			pageTable.put(pageNum, pte);
			numAccesses++;
		}

		// display statistics
		printInfo("Clock", numFrames, numAccesses, numPageFaults, numDiskWrites);
	}


	// not recently used
	public void nru(String traceFile, int numFrames, int refresh) throws FileNotFoundException{
		int numAccesses = 0;
		int numPageFaults = 0;
		int numDiskWrites = 0;
		int[] frames = new int[numFrames];


		Hashtable<Integer, PageTableEntry> pageTable = new Hashtable<Integer, PageTableEntry>();
		Scanner sc = new Scanner(new File(traceFile));


		for(int i = 0; i < 1024*1024; i++){
			PageTableEntry pte = new PageTableEntry();
			pageTable.put(i, pte);
		}


		for(int i = 0; i < numFrames; i++){
			frames[i] = -1;
		}

		int currFrame = 0;
		while(sc.hasNext()){

			if(numAccesses % refresh == 0 && numAccesses != 0){

				// update page table
				for(int i = 0; i < currFrame; i++){
					PageTableEntry pte = pageTable.get(frames[i]);
					pte.referenced = false;
					pageTable.put(pte.index, pte);
				}
			}

			String [] line = sc.nextLine().split(" ");
			int pageNum = Integer.decode("0x" + line[0].substring(0,5));

			PageTableEntry pte = pageTable.get(pageNum);
			pte.index = pageNum;
			pte.referenced = true;

			if(line[1].equals("W")){
				pte.dirty = true;
			}

			if(pte.valid){
				System.out.println(line[0] + " hit");
			} else {
				numPageFaults++;

				// no eviction
				if(currFrame < numFrames){
					frames[currFrame] = pageNum;
					pte.frame = currFrame;
					pte.valid = true;
					currFrame++;

					System.out.println(line[0] + " page fault -- no eviction");
				}
				// evicting
				else{
					PageTableEntry pageToEvict = null;
					boolean foundFlag = false;

					while(!foundFlag){

						for(int i = 0; i < numFrames; i++){
							PageTableEntry tempPTE = pageTable.get(frames[i]);

							// good page
							if(tempPTE.valid && !tempPTE.referenced && !tempPTE.dirty){
								pte.frame = tempPTE.frame;

								if(pte.dirty){
									System.out.println(line[0] + " page fault -- evict dirty");
									numDiskWrites++;
								} else {
									System.out.println(line[0] + " page fault -- evict clean");
								}

								frames[pte.frame] = pte.index;

								tempPTE.valid = false;
								tempPTE.dirty = false;
								tempPTE.referenced = false;
								tempPTE.frame = -1;
								pageTable.put(tempPTE.index, tempPTE);

								pte.valid = true;
								pageTable.put(pte.index, pte);

								foundFlag = true;
								break;
							}
							else{
								if(tempPTE.valid && tempPTE.dirty && !tempPTE.referenced){
									pageToEvict = new PageTableEntry(tempPTE);
									continue;
								}
								else if(tempPTE.valid && !tempPTE.dirty && tempPTE.referenced && pageToEvict == null){
									pageToEvict = new PageTableEntry(tempPTE);
									continue;
								}
								else if(tempPTE.valid && tempPTE.dirty && tempPTE.referenced && pageToEvict == null){
									pageToEvict = new PageTableEntry(tempPTE);
									continue;
								}
							}
						}
					}

					if(foundFlag){
						continue;
					}

					pte.frame = pageToEvict.frame;

					if(pageToEvict.dirty){
						System.out.println(line[0] + " page fault -- evict dirty");
						numDiskWrites++;
					} else {
						System.out.println(line[0] + " page fault -- evict clean");
					}

					frames[pte.frame] = pte.index;

					pageToEvict.valid = false;
					pageToEvict.dirty = false;
					pageToEvict.referenced = false;
					pageToEvict.frame = -1;
					pageTable.put(pageToEvict.index, pageToEvict);

					pte.valid = true;
					pageTable.put(pte.index, pte);

					foundFlag = false;
				}
			}
			pageTable.put(pageNum, pte);
			numAccesses++;
		}

		// display statistics
		printInfo("NRU", numFrames, numAccesses, numPageFaults, numDiskWrites);
	}


	public void random(String traceFile, int numFrames) throws FileNotFoundException{
		int numAccesses = 0;
		int numPageFaults = 0;
		int numDiskWrites = 0;
		int[] frames = new int[numFrames];



		Hashtable<Integer, PageTableEntry> pageTable = new Hashtable<Integer, PageTableEntry>();
		Scanner sc = new Scanner(new File(traceFile));


		for(int i = 0; i < 1024*1024; i++){
			PageTableEntry pte = new PageTableEntry();
			pageTable.put(i, pte);
		}

		for(int i = 0; i < numFrames; i++){
			frames[i] = -1;
		}

		int currFrame = 0;
		while(sc.hasNext()){
			String [] line = sc.nextLine().split(" ");
			int pageNum = Integer.decode("0x" + line[0].substring(0,5));


			PageTableEntry pte = pageTable.get(pageNum);
			pte.index = pageNum;
			pte.referenced = true;


			if(line[1].equals("W")){
				pte.dirty = true;
			}


			if(pte.valid){
				System.out.println(line[0] + " hit");
			} else {
				numPageFaults++;

				// no eviction
				if(currFrame < numFrames){
					frames[currFrame] = pageNum;
					pte.frame = currFrame;
					pte.valid = true;
					currFrame++;

					System.out.println(line[0] + " page fault -- no eviction");
				}
				// evicting
				else{
					PageTableEntry pageToEvict = null;

					// !! incomplete !!
				}





			pageTable.put(pageNum, pte);
			numAccesses++;
		}
		// display statistics
		printInfo("Random", numFrames, numAccesses, numPageFaults, numDiskWrites);
	}







	// prints out algorithm statistics
	private static void printInfo(String algoName, int numFrames, int numAccesses, int numPageFaults, int numDiskWrites){
		System.out.println("Algorithm: " + algoName);
		System.out.println("Number of frames: " + numFrames);
		System.out.println("Total memory accesses: " + numAccesses);
		System.out.println("Total page faults: " + numPageFaults);
		System.out.println("Total writes to disk: " + numDiskWrites);
	}
}
