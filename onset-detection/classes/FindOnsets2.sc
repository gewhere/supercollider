FindOnsets2 {

	var <>dataLeft, <>dataRight;

	*new { | path, dyad, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		^super.new.init(path, dyad, take, numOfFiles, window, outliers, diff)
	}

	init { | path, dyad, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		this.computeOnsets(path, dyad, take, numOfFiles, window, outliers, diff);
	}

	computeOnsets { | path, dyad, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		var outLeft, outRight;

		// PopulatedData for Left channel
		dataLeft = this.populateData( path, dyad, "L", take, numOfFiles, window, outliers, diff );
		"dataLeft: ".post; dataLeft.postln;
		// PopulatedData for Right channel
		dataRight = this.populateData( path, dyad, "R", take, numOfFiles, window, outliers, diff );
		"dataRight: ".post; dataRight.postln;

		outLeft = this.removeNoise(dataLeft, window, outliers);
		outRight = this.removeNoise(dataRight, window, outliers);
		//
		this.removeIdentical(outLeft, outRight, window);

	}

	populateData { | path, dyad, channel, take, numOfFiles, window=0.065, outliers=40, diff=0.030 |
		var which, tmp, data;
		which = path ++ dyad ++ "_" ++ channel ++ "." ++ take ++ "-";
		tmp = PopulatedData(which, numOfFiles);
		data = tmp.array;
		^data
	}

	removeNoise { | array, window=0.065, outliers=40 |
		var tmp;
		var out;

		tmp = RemoveNoise(array, window);
		tmp.removeOutliers(outliers);
		out = tmp.outputArray;
		"HEI".postln;
		^out
	}

	removeIdentical { | arrayLeft, arrayRight |
		var tmp, tmpLeft, tmpRight;

		tmp = RemoveIdentical(arrayLeft, arrayRight);
		"tmp.arrayLeft: ".post; tmp.arrayLeft.postln;
		"tmp.arrayRight: ".post; tmp.arrayRight.postln;
		// here repeat the step RemoveNoise
		tmpLeft = RemoveNoise(tmp.arrayLeft, 0.120);
		tmpRight = RemoveNoise(tmp.arrayRight, 0.120);
		// now I don't have to .removeOutliers
		// so proceeding to .mergeOnsets instead
		this.filteredIOI(tmpLeft.mergeOnsets);
		this.filteredIOI(tmpRight.mergeOnsets);
	}

	filteredIOI { | array |
		var tmp;
		tmp = FilteredIOI(array);
		tmp.outputArray;
	}
}
