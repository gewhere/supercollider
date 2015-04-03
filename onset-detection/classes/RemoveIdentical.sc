RemoveIdentical {
	var <>arrayLeft;
	var <>arrayRight;

	*new { | arrayL, arrayR, id=0.050 |
		// id => time between identical onsets in the two arrays LR
		^super.new.init(arrayL, arrayR, id)
	}

	init { | arrayL, arrayR, id=0.050 |
		this.removeElements(arrayL, arrayR, id)
	}

	removeElements { | arrayL, arrayR, id=0.050 |
		var copyLeft;
		var copyRight;
		var currDiff;
		var tmp;
		
		var counter = 0;
		var dict = IdentityDictionary.new;

		copyLeft = arrayL;
		copyRight = arrayR;

		// put in a staged state elements which have IOI less than threshold (id) (first condition)
		arrayL.size do: { |i|
			arrayR.size do: { |j|
				currDiff = ( copyLeft[i][0] - copyRight[j][0] ).abs;
				if ( currDiff < id ) {
					dict.put( counter.asSymbol, [i, j] );
					counter = counter + 1;
				};
			};
		};
		"dict: ".post; dict.postln;

		// remove elements based on weights (second condition) [onsets_value, onset_weight]
		dict.size do: { |k|
			tmp = dict.at( k.asSymbol );
			"tmp: ".post; tmp.postln;
			// this block compares the weights
			case
			{ copyLeft[ tmp[0] ][ 1 ] < copyRight[ tmp[1] ][ 1 ] }{
				copyLeft[ tmp[0] ] = nil;
			}
			{ copyLeft[ tmp[0] ][ 1 ] > copyRight[ tmp[1] ][ 1 ] }{
				copyRight[ tmp[1] ] = nil;
			};

		};
		arrayLeft = copyLeft.removeNils;
		"arrayLeft.size = ".post; arrayLeft.size.postln;
		//
		arrayRight = copyRight.removeNils;
		"arrayRight.size = ".post; arrayRight.size.postln;
	}
}
