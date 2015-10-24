(define (problem Align) (:domain Mining)
(:objects
A1 A2 B1 B2 C1 D1 E1 F1 G1 H1 - task
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
)
(:goal
(not_precedence A1 B2)
;(and 
;;;;;
;(or
;(choice H1 A1)
;(choice H1 A2)
;)
;;;;;
;(or 
;(responseWithDataTrue A1 B1 var1) 
;(responseWithDataTrue A1 B2 var1)
;)
;(or 
;(responseWithDataTrue A2 B1 var1) 
;(responseWithDataTrue A2 B2 var1)
;)
;;;;;
;)
)
;;(:metric minimize (total-time))
)
