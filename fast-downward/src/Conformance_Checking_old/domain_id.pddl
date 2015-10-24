;We cannot use integer or boolean parameters as inputs
;[LPG-td] - Some predicate needs to have at least an instantiation in the planning problem, otherwise it does not work.

(define (domain Mining)
(:requirements :derived-predicates :typing :equality)
(:types task identifier) 
		
(:predicates 
(pre ?x - task ?i1 - identifier ?y - task ?i2 - identifier)
(free ?i - identifier)
(traced ?x - task ?i - identifier)
;;
;;LTL rules
;;
(precedence ?x - task ?y - task)
)

;(:functions
;(numberOfBonds ?x - molecule)
;)

;PLANNING ACTIONS FOR # A

(:action addAfter
:parameters (?x1 - task ?i1 - identifier ?x2 - task ?i2 - identifier ?x3 - task ?i3 - identifier) 
:precondition (and (pre ?x1 ?i1 ?x2 ?i2) (free ?i3) (not (exists (?i4 - identifier) (traced ?x3 ?i4))))
:effect (and (pre ?x1 ?i1 ?x3 ?i3) (pre ?x3 ?i3 ?x2 ?i2) (not (pre ?x1 ?i1 ?x2 ?i2)) (traced ?x3 ?i3) (not (free ?i3))))


;(:action delete
;:parameters (?x1 - task ?i1 - identifier ?x2 - task ?i2 - identifier ?x3 - task ?i3 - identifier) 
;:precondition (and (traced ?x1) (traced ?x2) (traced ?x3) (pre ?x1 ?x2) (pre ?x2 ?x3) (assigned ?x1 ?i1) (assigned ?x2 ?i2) (assigned ?x3 ?i3))
;:effect (and (pre ?x1 ?x3) (not (pre ?x1 ?x2)) (not (pre ?x2 ?x3)) (not (assigned ?x2 ?i2)) (not (traced ?x2)) (free ?i2))
;)

;DERIVED PREDICATES

;;
;;LTL PRECEDENCE
;;
(:derived (precedence ?t1 - task ?t2 - task) 
	      (or (not (exists (?i - identifier) (traced ?t1 ?i)))
	          (exists (?i1 - identifier ?i2 - identifier) (pre ?t1 ?i1 ?t2 ?i2))
	          (exists (?t3 - task ?i1 - identifier ?i3 - identifier) (and (pre ?t1 ?i1 ?t3 ?i3) (precedence ?t3 ?t2)))))

;(:derived (ester ?c - carbon ?o - oxygen) 
;	  (and (bond ?c ?o) (exists (?o2 - oxygen ?c2 - carbon ?c3 - carbon) 
;	  	                    (and (not (= ?o ?o2)) (doublebond ?o2 ?c) (not (= ?c ?c2)) (not (= ?c ?c3)) 
;				         (not (= ?c2 ?c3)) (bond ?c ?c2) (bond ?o ?c3) (alkyl ?c2) (alkyl ?c3))))
;)


)
