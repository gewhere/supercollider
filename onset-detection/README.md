# Onsets detection algorithm for dyadic violin performance recordings
This is an algorithm based on batch processing onsets extraction using SCMIR library of SuperCollider.  Specifically I am using the method `extractOnsets`.  There are three main steps:
- the batch onsets extraction
- statistical analysis for estimate the onsets
- verify the results using plots, and save onsets

## Batch onsets extraction
The folder batch-extraction contains the language-side files for the batch processing.  For each batch I was editing the `MultipleOnsets.sc` class respectively.

## Statistical analysis
The folder classes contains all the classes.  The final product is the `FindOnsets.sc` class.  The demo for the lang-side is the `demo-FindOnsets.scd` file.  The files `demo-model.scd` and `onsets-detection-procedure.scd` are precursors that led the development of the `FindOnsets.sc` class.
