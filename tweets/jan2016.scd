// minimal poly-rhythms
play{ a=SinOsc; FreeVerb2.ar(x=a.ar(1069+1000*a.kr(12*a.kr(11)))*GrainSin.ar(2,a.kr(5*a.kr(6*a.kr(7))),0.002,288+178*a.kr(8)),x) };

// minimal poly-rhythms too
play{a=SinOsc;FreeVerb2.ar(Ringz.ar(x=GrainSin.ar(2,a.ar(13),5e-4*a.kr(11).abs,1134+123*a.kr(7),a.kr(15)),800+500*a.kr(3),0.02),x,0.2)/4}