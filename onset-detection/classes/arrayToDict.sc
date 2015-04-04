+Array {
	arrayToDict {
		var array;
		var dict = Dictionary.new;
		
		if( this.notNil && this.isArray ){
			array = this;
		};

		array.size do: { |i|
			dict.put( i.asSymbol, array[i] )
		};

		^dict

	}

}