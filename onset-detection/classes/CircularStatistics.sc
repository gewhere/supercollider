CircularStatistics {

	var <tempo;
	var <>ioiLeft, <>ioiRight;
	var <starterLeft, <starterRight; /// which player begins 
	var <tempoPhiLeft, <tempoPhiRight; // 
	var <radiusLeft, <radiusRight;

	*new { | dict |
		^super.new.init( dict )
	}

	init { | dict |
		this.readTakes( dict );
		//		this.computeSelf( dict );
//		this.computeTempo( dict );
//		this.computeEntrainment( dict );
	}

	readTakes { | dict |
		var ioiLeft, ioiRight, cnt;
		var normalizedLeft, tmpL, normalizedRight, tmpR; // normaArrays => are normalized so their 1st element being '0'
		dict.postln;
		dict.class;
		// this loop creates arrays omputing IOI and normalized values (see above) for each one of the 24 takes
		( dict.size / 2 ) do: { |i|
			//i.postln;
			cnt = i + 1;
			"take: ".post; cnt.postln;
			tmpL = dict[ ("L"++cnt).asSymbol ];
			//"tmpL >> ".post; tmpL.postln;
			normalizedLeft = tmpL collect: { |index| index - tmpL[0] };
			//"normL: ".post; normalizedLeft.postln;
			//
			tmpR = dict[ ("R"++cnt).asSymbol ];
			//"tmpR << ".post; tmpR.postln;
			normalizedRight = tmpR collect: { |index| index - tmpR[0] };
			//"normR: ".post; normalizedRight.postln;
			//
			if( tmpL[0] < tmpR[0] ) {
				starterLeft = 1; starterRight = 2;
			}{
				starterLeft = 2; starterRight = 1;
			};
			//
			this.estimateTempo( normalizedLeft, normalizedRight );
			this.stabilityLeft( normalizedLeft );
			this.stabilityRight( normalizedRight );
		};
	}

	estimateTempo { | arrayLeft, arrayRight |
		var meanTempo;
		
		//"arrayLeft: ".post; arrayLeft.postln; arrayLeft.class.postln;
		ioiLeft = arrayLeft.findIOI;
		//"ioiLeft: ".post; ioiLeft.postln;
		ioiRight = arrayRight.findIOI;
		//"ioiRight: ".post; ioiRight.postln;
		// is divided by 4 because each performer plays one note at a time
		meanTempo = ( ioiLeft.mean + ioiRight.mean ) / 4;

		// confirm tempo with excell file !!!
		case
		{  meanTempo > 0.85 } { tempo = [ 1.0, '60bpm']  } //'60bpm'
		{  ( meanTempo > 0.60 ) && ( meanTempo < 0.85 ) } { tempo = [ 0.666667, '90bpm' ] } // '90bpm'
		{  ( meanTempo > 0.45 ) && ( meanTempo < 0.55 ) } { tempo = [ 0.5, '120bpm' ] } // '120bpm'
		{  meanTempo < 0.45 } { tempo = [ 0.4, '150bpm' ] }; //'150bpm'

		//"tempo: ".post; tempo.postln;
	}

	stabilityLeft { | arrayLeft |
		var phiLeft; // , ioiLeft;
		var asynchLeft;
		var copyLeft;

		copyLeft = arrayLeft;
		// array.size - 1 => this is quite problematic as I don't have the first value of each take with reference to the metronome
		// there 2 possible approaches:
		// a. assume the first note has a phase = 0 degrees
		// b. reject the first value (which is what I am doing here)
		tempoPhiLeft = Array.newClear( copyLeft.size - 1 );
		// IOI arrays (their length is -1 element of the arrays with onsets)
		
		( copyLeft.size - 1 ) do: { |i|
			// this computes stability to the metronome
			asynchLeft = ( tempo[0]*(i + starterLeft ) )  - copyLeft[i];
			// "copyLeft".post; copyLeft.postln;
			// "asynchLeft: ".post; asynchLeft.postln;
			phiLeft = 2*pi + (2*pi)*( asynchLeft / ioiLeft[i] );
			// "LEFT i: ".post; i.postln;
			//"phiLeft = ".post; phiLeft.postln;
			// array with the phi values
			tempoPhiLeft[i] = phiLeft; // an array with all the values of phi angle
			// this angle should be used to calculate R mean vector
			// Rmean_y = sin( phi )
			// Rmean_x = cos( phi )
			// | Rmean | = ( Rmean_x[ i+1 ] + Rmean_x[ i ] + Rmean_y[ i+1 ] + Rmean_y[ i ] ) / 2
		};
		// calculates R mean
		( tempoPhiLeft.size - 1 ) do: { |i|
			radiusLeft = ( sin( tempoPhiLeft[i] ) + cos( tempoPhiLeft[i] ) + sin( tempoPhiLeft[i+1] ) + cos( tempoPhiLeft[i+1] ) ) / 2;
		};
		"tempo: ".post; tempo[1].postln;
		"radiusLeft = ".post; radiusLeft.postln;
		"tempoPhiLeft: ".post; tempoPhiLeft.last.postln;
	}

	stabilityRight { | arrayRight |
		var phiRight; //, ioiRight;
		var asynchRight;
		var copyRight;

		copyRight = arrayRight;
		tempoPhiRight = Array.newClear( copyRight.size - 1 );
		//
		( copyRight.size - 1 ) do: { |i|
			// this computes stability to the metronome
			asynchRight = ( tempo[0]*(i + starterRight) ) - copyRight[i];
			phiRight = 2*pi + (2*pi)*( asynchRight / ioiRight[i] );
			//"RIGHT i: ".post; i.postln;
			//"phiRight = ".post; phiRight.postln;
			// array with the phi values
			tempoPhiRight[i] = phiRight;
		};
		// calculates R mean
		( tempoPhiRight.size - 1 ) do: { |i|
			radiusRight = ( sin( tempoPhiRight[i] ) + cos( tempoPhiRight[i] ) + sin( tempoPhiRight[i+1] ) + cos( tempoPhiRight[i+1] ) ) / 2;
		};
		
		"radiusRight = ".post; radiusRight.postln;
		"tempoPhiRight: ".post; tempoPhiRight.last.postln;
		
	}

}