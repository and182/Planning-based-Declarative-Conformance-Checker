(define (problem Align) (:domain Mining)
(:objects
a1 a2 a3 - A
b1 b2 - B
c1 - C
d1 - D
e1 - E 
f1 - F 
g1 - G 
)
(:init
(pre a1 c1)
(pre c1 a2)
(pre a2 d1)
(pre d1 a3)
(traced a1)
(traced a2)
(traced a3)
(traced c1)
(traced d1)
)
(:goal
(existence-B)
)
;;(:metric minimize (total-time))
)
