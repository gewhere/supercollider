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
		var currDiff;
		var tmp;
		var tmpLeft, tmpRight, checkLeft, checkRight;
		var copyLeft;
		var copyRight;
		var cnt = 0;
		var dict = Dictionary.new;

		copyLeft = arrayL;
		copyRight = arrayR;

		// put in a staged state elements which have IOI less than threshold (id) (first condition)
		arrayL.size do: { |i|
			arrayR.size do: { |j|
				currDiff = ( copyLeft[i][0] - copyRight[j][0] ).abs;
				if ( currDiff < id ) {
					dict.put( ('L'++cnt).asSymbol, i ); // put elements of Left array
					dict.put( ('R'++cnt).asSymbol, j ); // put elements of Right array
					cnt = cnt + 1;
				};
			};
		};
		"dict: ".post; dict.postln;
		//

		// remove elements based on weights (second condition) [onsets_value, onset_weight]
		( dict.size/2 ) do: { |k|
			tmpLeft = dict.at( ('L'++k).asSymbol ); // this is i (see line:31)
			tmpRight = dict.at( ('R'++k).asSymbol ); // this is j (see line:32)
			"tmpLeft: ".post; tmpLeft.postln;
			"tmpRight: ".post; tmpRight.postln;
			// this block compares the weights ( copyLeft/Right are (1x2) arrays )
			case
			{ copyLeft[ tmpLeft ][ 1 ] < copyRight[ tmpRight ][ 1 ] }{
				copyLeft[ tmpLeft ] = nil;
			}
			{ copyLeft[ tmpLeft ][ 1 ] > copyRight[ tmpRight ][ 1 ] }{
				copyRight[ tmpRight ] = nil;
			};

		};
		arrayLeft = copyLeft.removeNils;
		"arrayLeft.size = ".post; arrayLeft.size.postln;
		//
		arrayRight = copyRight.removeNils;
		"arrayRight.size = ".post; arrayRight.size.postln;
	}
}