(
~dyad = "9";
~sndFilePath = "/home/aucotsi/data/waveFilesNoMetro/Marc_Dyad_0";
~path = "/home/aucotsi/data/onsetsData/onsetsDyads/dyads_LR_411/"; // dyad7_L_108
~take="24";
)
a = FindOnsets2(path: ~path, dyad: ~dyad, take: ~take, numOfFiles:411, window:0.065, outliers: 40);
(
a.left.postln;
a.left.size.postln;
a.right.postln;
a.right.size.postln;
)
thisProcess.recompile;
//
// GNUPLOT (plot signal)
(
~pathLEFT = ~sndFilePath ++ ~dyad ++ "_"++ ~take.asString ++".L.wav";
~pathRIGHT = ~sndFilePath ++ ~dyad ++ "_"++ ~take.asString ++".R.wav";
~leftCHANNEL = PlotOnsets(~pathLEFT);
~rightCHANNEL = PlotOnsets(~pathRIGHT);
)
// plot onsets
(
~leftCHANNEL.plotOnsets(a.left, "set title \"Left Channel\"");
~rightCHANNEL.plotOnsets(a.right, "set title \"Right Channel\"");
)
//
SaveOnsets(a.left, a.right, ~dyad, ~take,  "/home/aucotsi/code/orgmode/research/cmmr2015/onsetsNew/")