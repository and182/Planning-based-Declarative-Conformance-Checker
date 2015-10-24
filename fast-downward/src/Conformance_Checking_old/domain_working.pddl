;We cannot use integer or boolean parameters as inputs
;[LPG-td] - Some predicate needs to have at least an instantiation in the planning problem, otherwise it does not work.

(define (domain Mining)
(:requirements :derived-predicates :typing :equality)
(:types task data) 
		
(:predicates 
(pre ?x - task ?y - task)
(traced ?x - task)
(value ?x - task ?d - data)
;;
;;LTL rules
;;
(response ?x - task ?y - task)
(responseWithDataTrue ?x - task ?y - task ?d - data)
(notresponse ?x - task ?y - task)
)


;PLANNING ACTIONS

;
; If x1 is the first task of the trace, the action adds x2 before x1
;
(:action addAtTheBeginning
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (not (traced ?x2)) (not (exists (?x3 - task) (pre ?x3 ?x1))))
:effect (and (traced ?x2) (pre ?x2 ?x1))
)

;
; If x1 is the last task of the trace, the action adds x2 after x1
;
(:action addAtTheEnd
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (not (traced ?x2)) (not (exists (?x3 - task) (pre ?x1 ?x3))))
:effect (and (traced ?x2) (pre ?x1 ?x2))
)

(:action addBetween
:parameters (?x1 - task ?x2 - task ?x3 - task) 
:precondition (and (pre ?x1 ?x2) (not (traced ?x3)))
:effect (and (traced ?x3) (pre ?x1 ?x3) (pre ?x3 ?x2) (not (pre ?x1 ?x2)))
)

;
; Delete x1; If x1 is the first task of the trace, and the action x2 follows x1, x2 will become the new first task of the trace
;
(:action deleteAtTheBeginning
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (traced ?x2) (pre ?x1 ?x2) (not (exists (?x3 - task) (pre ?x3 ?x1))))
:effect (and (not (traced ?x1)) (not (pre ?x1 ?x2)))
)

(:action deleteBetween
:parameters (?x1 - task ?x2 - task ?x3 - task)
:precondition (and (traced ?x1) (traced ?x2) (traced ?x3) (pre ?x1 ?x2) (pre ?x2 ?x3))
:effect (and (pre ?x1 ?x3) (not (pre ?x1 ?x2)) (not (pre ?x2 ?x3)) (not (traced ?x2)))
)

;DERIVED PREDICATES

;;
;;LTL RESPONSE
;;
(:derived (response ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
	          (pre ?t1 ?t2)
	          (exists (?t3 - task) (and (pre ?t1 ?t3) (response ?t3 ?t2)))))

;;
;;LTL RESPONSE WITH DATA
;;
(:derived (responseWithDataTrue ?t1 - task ?t2 - task ?d - data) 
	      (or (not (traced ?t1))
		  (and (traced ?t1) (not (value ?t1 ?d)))
	          (and (value ?t1 ?d) (pre ?t1 ?t2))
	          (and (value ?t1 ?d) (exists (?t3 - task) (and (pre ?t1 ?t3) (response ?t3 ?t2))))))

;;
;;LTL NOT RESPONSE
;;
(:derived (notresponse ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
	          (not (traced ?t2))
		  (and (not (response ?t1 ?t2)))))

;(:derived (ester ?c - carbon ?o - oxygen) 
;	  (and (bond ?c ?o) (exists (?o2 - oxygen ?c2 - carbon ?c3 - carbon) 
;	  	                    (and (not (= ?o ?o2)) (doublebond ?o2 ?c) (not (= ?c ?c2)) (not (= ?c ?c3)) 
;				         (not (= ?c2 ?c3)) (bond ?c ?c2) (bond ?o ?c3) (alkyl ?c2) (alkyl ?c3))))
;)


)
