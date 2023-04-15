**WHY IS PURE LEVENSHTEIN DISTANCE ALGORITHM MORE EFFECTIVE
THAN THE BROADER DAMERAU LEVENSHTEIN DISTANCE ALGORITHM**
        
```agsl   

   If nearby character transpositions are unlikely,
    the more specialized Damerau-Levenshtein Distance 
    technique may not be necessary compared to the more 
    general Levenshtein Distance method. This is because
     the Damerau-Levenshtein Distance algorithm takes
      transpositions into account, which can make computations
       more difficult. When validating names, transpositions 
       of nearby characters are not common, so a pure Levenshtein 
       Distance method may be more efficient and cost-effective.
        However, if transpositions are common, then the Damerau-Levenshtein
         Distance method would be more suitable.
    
```



                            TESTING
```agsl                            
   I utilized Junit5 and Mockito to create tests for all of the implementations.
```

