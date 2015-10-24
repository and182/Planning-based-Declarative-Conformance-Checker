(define (problem Align) (:domain Mining)
(:objects
A1 A2 B1 B2 C1 D1 E1 F1 G1 H1 - task
var1 - data
)
(:init
(pre A1 B1)
(pre B1 C1)
(pre C1 A2)
(pre A2 D1)
(traced A1)
(traced A2)
(traced B1)
(traced C1)
(traced D1)
(value A1 var1)
)
(:goal
;(and 
;(or 
;(responseWithDataTrue A1 B1 var1) 
;(responseWithDataTrue A1 B2 var1)
;)
;(or 
;(responseWithDataTrue A2 B1 var1) 
;(responseWithDataTrue A2 B2 var1)
;)
;)
(corr-response A1 A2 var1)
;(:metric minimize (total-time))
)
)
