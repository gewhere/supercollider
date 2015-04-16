FindOnsets2 {

	var <>dataLeft, <>dataRight;

	*new { | path, dyad, take, numOfFiles, window=0.65, outliers=40, diff=0.030 |
		^super.new.init(path, dyad, take, numOfFiles, window, outliers, diff)
	}

	init { | path, dyad, take, numOfFiles, window=0.65, outliers=40, diff=0.030 |
		this.computeOnsets(path, dyad, take, numOfFiles, window, outliers, diff);
	}

	computeOnsets { | path, dyad, take, numOfFiles, window=0.65, outliers=40, diff=0.030 |
		
		// PopulatedData for Left channel
		dataLeft = this.populateData( path, dyad, "L", take, numOfFiles, window, outliers, diff );
		"dataLeft: ".post; dataLeft.postln;
		// PopulatedData for Right channel
		dataRight = this.populateData( path, dyad, "R", take, numOfFiles, window, outliers, diff );
		"dataRight: ".post; dataRight.postln;
	}

	populateData { | path, dyad, channel, take, numOfFiles, window=0.65, outliers=40, diff=0.030 |
		var which, data;

		which = path ++ dyad ++ "_" ++ channel ++ "." ++ take ++ "-";
		data = PopulatedData(which, numOfFiles);
	}

	

}