RemoveNoise {

	var <>cnt = 0;
	var <>array, <>diff; // 50ms
	var <>mergeOnsets;
	var <>outputArray;

	*new { | array, diff, minimum=10 |
		^super.new.init(array, diff, minimum)
	}

	init { | array, diff, minimum=10 |
		this.strictWindow(array, diff, minimum)
	}

	strictWindow { | array, diff, minimum |
		var weights, item, arraySize, copyOnsets;
		mergeOnsets = array; //.round(0.001);
		copyOnsets = array;
		arraySize = mergeOnsets.size;

		(arraySize - 1) do: { |i|
			// i.postln;
			if ( ( copyOnsets[i].notNil ) && ( (mergeOnsets[i+1][0] - mergeOnsets[i][0]) <= diff ) ) {
				weights = mergeOnsets[i][1] + mergeOnsets[i+1][1];
				item = ( mergeOnsets[i][0]*( mergeOnsets[i][1] / weights ) ) + ( mergeOnsets[i+1][0]*( mergeOnsets[i+1][1] / weights ) );
				copyOnsets[i+1] = [item, weights];
				copyOnsets[i] = nil;
			};
		};
		mergeOnsets = copyOnsets.removeNils;
	}

	removeOutliers { | minimum = 10 |
		var initSize = mergeOnsets.size;

		initSize do: { |i|
			if ( mergeOnsets[i][1] < minimum ) {
				mergeOnsets[i] = nil;
			};
		};
		// remove nil elements from the array
		mergeOnsets = mergeOnsets.removeNils;
		outputArray = mergeOnsets;
		"mergeOnsets.size: ".post; outputArray.size.postln;
	}

}
