FindOnsets {

	var <>dataLeft, <>dataRight;
	var <left, <right;

	*new { | path, dyad, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		^super.new.init(path, dyad, take, numOfFiles, window, outliers, diff)
	}

	init { | path, dyad, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		this.computeOnsets(path, dyad, take, numOfFiles, window, outliers, diff);
	}

	computeOnsets { | path, dyad, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		var outLeft;
		var outRight;
	
		// PopulatedData for Left channel
		dataLeft = this.populateData( path, dyad, "L", take, numOfFiles, window, outliers, diff );
		"dataLeft: ".post; dataLeft.postln;
		// PopulatedData for Right channel
		dataRight = this.populateData( path, dyad, "R", take, numOfFiles, window, outliers, diff );
		"dataRight: ".post; dataRight.postln;
		outLeft = this.removeNoise(dataLeft, window, outliers);
		outRight = this.removeNoise(dataRight, window, outliers);
		// calling RemoveIdentical
		this.removeIdentical(outLeft, outRight, window);
	}

	populateData { | path, dyad, channel, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		var which;
		var tmp;
		var data;
		// var which holds the path of the waveFiles
		which = path ++ "dyad" ++ dyad ++ "_" ++ channel ++ "." ++ take ++ "-";
		tmp = PopulatedData(which, numOfFiles);
		data = tmp.array;
		^data
	}

	removeNoise { | array, window=0.065, outliers=40 |
		var tmp;
		var out;
		// RemoveNoise is the algorithm for merging the onsets
		tmp = RemoveNoise(array, window);
		tmp.removeOutliers(outliers);
		out = tmp.outputArray;
		"HEI".postln;
		^out
	}

	removeIdentical { | arrayLeft, arrayRight |
		var tmp;
		var tmpLeft;
		var tmpRight;

		tmp = RemoveIdentical(arrayLeft, arrayRight, 0.030);
		"tmp.arrayLeft: ".post; tmp.arrayLeft.size.postln;
		"tmp.arrayRight: ".post; tmp.arrayRight.size.postln;
		// here I repeat the step RemoveNoise
		tmpLeft = RemoveNoise(tmp.arrayLeft, 0.120);
		tmpRight = RemoveNoise(tmp.arrayRight, 0.120);
		// now I don't have to .removeOutliers (as in removeNoise method)
		// so proceeding to .mergeOnsets instead
		left = this.filteredIOI(tmpLeft.mergeOnsets, "left");
		right = this.filteredIOI(tmpRight.mergeOnsets, "right");
	}

	filteredIOI { | array, channel |
		var tmp;
		var old;
		var i=0;

		tmp = FilteredIOI(array);
		old = array.size; // => this is assigned to tmpSize
		"old: ".post; old.postln;
		// this is a fast solution assuming that the FilteredIOI does not need no more that 5 repetitions
		// try to implement this using while loop 
		5 do: { |i|
			tmp = FilteredIOI(tmp.outputArray);
			//
			if (channel == "left"){
				" LEFT CHANNEL <<<<<<<<".postln;
			}{
				" RIGHT CHANNEL <<<<<<<<".postln;
			};
			old = tmp.size;
			tmp.outputArray.size.postln;
		};
		// return the output array using a = FindOnsets(); a.left; OR a.right;
		^tmp.outputArray;		
	}

}