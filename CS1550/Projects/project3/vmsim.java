/*
 * Project 3
 * By: John Kelly Jr
 *
 * usage: ./vmsim -n <numframes> -a <algotype> [-r <refresh>] <tracefile>
 */


 import java.io.FileNotFoundException;


 public class vmsim{

	 public static void main(String[] args) throws FileNotFoundException{
		int frameNum = 0;
		int refresh = 0;
		String algo = "";
		String traceFile = "";

		Simulator vm = new Simulator();

		for(int i = 0; i < args.length; i++){

			if(args[i].equals("-n")){
				frameNum = Integer.parseInt(args[i + 1]);

				if(frameNum != 8 && frameNum != 16 && frameNum != 32 && frameNum !=64){
					System.out.println("Please enter 8, 16, 32, or 64.");
					return;
				}
			}
			else if(args[i].equals("-a")){
				algo = args[i + 1];
			}
			else if(args[i].equals("-r")){
				refresh = Integer.parseInt(args[i + 1]);
			}
		}

		traceFile = args[args.length - 1];

		// System.out.println("numFrames: " + frameNum);
		// System.out.println("algo: " + algo);
		// System.out.println("traceFile: " + traceFile);


		if(algo.equals("opt")){
			vm.opt(traceFile, frameNum);
		}
		else if(algo.equals("clock")){
			vm.clock(traceFile, frameNum);
		}
		else if(algo.equals("nru")){
			vm.nru(traceFile, frameNum, refresh);
		}
		else if(algo.equals("random")){
			vm.random(traceFile, frameNum);
		}
		else{
			System.out.println("Error: please enter in a valid algorithm.");
		}
	 }
 }
