// minimal poly-rhythms
play{ a=SinOsc; FreeVerb2.ar(x=a.ar(1069+1000*a.kr(12*a.kr(11)))*GrainSin.ar(2,a.kr(5*a.kr(6*a.kr(7))),0.002,288+178*a.kr(8)),x) };