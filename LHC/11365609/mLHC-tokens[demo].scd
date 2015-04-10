// load SynthDef
SynthDef(\sine, { |freq| Out.ar(0, SinOsc.ar(freq!2)*Line.ar(1,0,1,1,0,2)/5) }).add;
//
(
~tokenLength = 0;
//:
a = LHC((
	symbol: { | self, symbol |
		if (self.soundOn) {
			(instrument: \sine, degree: (A: 11, B: 12, C: 13, D: 14)[symbol]).play;

			if(a.loadChord.value == nil) {
				~tokenLength = ~tokenLength + 1;
			}{
				~synchord = a.loadChord;
				// "chord: ".post; ~synchord.postln;
				// determine sequence playing with tokens
				~sequence = (
					tokenA: Pseq((11..12).mirror),
					tokenB: Pseq((11..13).mirror),
					tokenC: Pseq((11..14).mirror),
					tokenD: Pseq((12..14).mirror),
					tokenE: Pseq((13..14).mirror),
					tokenF: Pseq((13..15).mirror),
					tokenG: Pseq((13..16).mirror)
				)[~synchord];
				// play pattern
				Pbind(\instrument, \sine, \degree, ~sequence, \dur, a.waitTime/4).play;
				//
				~tokenLength = 0;
				a.loadChord = nil;
			}
		}
	},
	counter: { | self, counter |
		if (counter > 0) {
			(instrument: \sine, degree: counter).play;
			self.soundOn = true;
		}{
			self.soundOn = false
		}
	}
)).start;
)
// change waitTime
a.waitTime = 0.65;