ReadFiles {

	var <iddict;

	*new { | path, howMany |
		^super.new.init( path, howMany )
	}

	init { | path, howMany |
		iddict = IdentityDictionary.new;
		this.readBatch( path, howMany )
	}

	readBatch { | path, howMany |
		var cnt, file, strData;

		{
			howMany do: { |i|
				cnt = i+1;
				// cnt.postln;
				file = File.new(path ++ (i+1).asString ++ ".data", "rb");
				strData = file.readAllString;
				this.separateChannels(strData, cnt);
			};
		}.fork

	}

	separateChannels { | str, take |
		var openArray, closeArray;
		var leftChannel, rightChannel;

		openArray = str.findRegexp("\\["); // open brackets
		closeArray = str.findRegexp("\\]"); // closed brackets

		openArray = openArray collect: { |index| index[0] };
		closeArray = closeArray collect: { |index| index[0] };

		leftChannel = str.select { |index, i| i < openArray[1] };
		rightChannel = str.select { |index, i| i > closeArray[0] };

		this.storeTake(leftChannel, rightChannel, take)
	}

	storeTake { | leftChannel, rightChannel, take |
		var left, right;

		iddict.put( ('L'++take).asSymbol, leftChannel.interpret; );
		iddict.put( ('R'++take).asSymbol, rightChannel.interpret; );

	}
}