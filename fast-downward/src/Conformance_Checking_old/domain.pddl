;We cannot use integer or boolean parameters as inputs
;[LPG-td] - Some predicate needs to have at least an instantiation in the planning problem, otherwise it does not work.

(define (domain Mining)
(:requirements :derived-predicates :typing :equality)
(:types task) 
		
(:predicates 
(pre ?x - task ?y - task)
(traced ?x - task)

;;
;;LTL rules
;;
(existence ?x - task)
(absence ?x - task)
(choice ?x - task ?y - task)
(ex_choice ?x - task ?y - task)
(res_existence ?x - task ?y - task)
(response ?x - task ?y - task)
(precedence ?x - task ?y - task)
(chain_response ?x - task ?y - task)
(chain_precedence ?x - task ?y - task)
(not_res_existence ?x - task ?y - task)
(not_response ?x - task ?y - task)
(not_precedence ?x - task ?y - task)
(not_chain_response ?x - task ?y - task)
(not_chain_precedence ?x - task ?y - task)
)


;PLANNING ACTIONS

;
; If x1 is the first task of the trace, the action --addAtTheBeginning-- adds x2 before x1
;
(:action addAtTheBeginning
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (not (traced ?x2)) (not (exists (?x3 - task) (pre ?x3 ?x1))))
:effect (and (traced ?x2) (pre ?x2 ?x1))
)

;
; If x1 is the last task of the trace, the action --addAtTheEnd-- adds x2 after x1
;
(:action addAtTheEnd
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (not (traced ?x2)) (not (exists (?x3 - task) (pre ?x1 ?x3))))
:effect (and (traced ?x2) (pre ?x1 ?x2))
)

;
; The action --addBetween-- inserts the task x3 between the tasks x1 and x2 (that are already present in the trace).
;
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

;;;;;;;
;;DERIVED PREDICATES
;;;;;;;

;;
;;EXISTENCE(A) - (FINALLY A) - A must occur at least once in the trace.
;;

(:derived (existence ?t1 - task) (traced ?t1))

;;
;;ABSENCE(A) - (NOT (FINALLY A)) - A never occur in the trace.
;;

(:derived (absence ?t1 - task) (not (traced ?t1)))

;;
;;CHOICE(A,B) - (FINALLY A OR FINALLY B) - A or B occur eventually in the trace. The formula is TRUE also if they both occur in the trace.
;;

(:derived (choice ?t1 - task ?t2 - task) (or (traced ?t1) (traced ?t2)))

;;
;;EXCLUSIVE_CHOICE(A,B) - (FINALLY A OR FINALLY B) AND (NOT (FINALLY A AND FINALLY B)) 
;; 			  Only A or B can occur eventually in the trace.
;;

(:derived (ex_choice ?t1 - task ?t2 - task) 
	  (or (and (traced ?t1) (not (traced ?t2)))
              (and (not (traced ?t1)) (traced ?t2))))
												
;;
;;RESPONDED_EXISTENCE(A,B) - (FINALLY A -> FINALLY B) - If A occurs in the trace, then B should occur before or after A.
;;

(:derived (res_existence ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1)) (traced ?t2)))


;;
;;RESPONSE(A,B) - (GLOBALLY (A -> FINALLY B)) - When A occurs in the trace, B should eventually occur after A.
;;

(:derived (response ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
	          (pre ?t1 ?t2)
	          (exists (?t3 - task) (and (pre ?t1 ?t3) (response ?t3 ?t2)))))

;;
;;PRECEDENCE(A,B) - ((NOT B) WEAK UNTIL A) - B should occur only if A has occurred before.
;;

(:derived (precedence ?t1 - task ?t2 - task) 		  
		  (or (not (traced ?t2))
		      (pre ?t1 ?t2)
		      (exists (?t3 - task) (and (pre ?t3 ?t2) (precedence ?t1 ?t3)))))


;;
;;CHAIN_RESPONSE(A,B) - (GLOBALLY (A -> NEXT B)) - When A occurs in the trace, B must occur the next step after A.
;;

(:derived (chain_response ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
	          (pre ?t1 ?t2)))

;;
;;CHAIN_PRECEDENCE(A,B) - (GLOBALLY (NEXT B -> A)) - B can occur only if A has occurred the step before.
;;

(:derived (chain_precedence ?t1 - task ?t2 - task) 		  
		  (or (not (traced ?t2))
		      (pre ?t1 ?t2)))
;;
;;NOT_RESPONDED_EXISTENCE(A,B) - (FINALLY A -> NOT (FINALLY B)) - If A occurs, B cannot occur.
;;
	
(:derived (not_res_existence ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1)) (not (traced ?t2))))
  
;;
;;NOT_RESPONSE - (GLOBALLY (A -> NOT (FINALLY B))) - Any occurrence of A cannot be eventually followed by B.
;;

(:derived (not_response ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
	          (not (traced ?t2))
		  (not (response ?t1 ?t2))))

;;
;;NOT_PRECEDENCE(A,B) - Any occurrence of B is not preceded by A
;;

(:derived (not_precedence ?t1 - task ?t2 - task) 		  
		  (or (not (traced ?t2))
		      (not (traced ?t1))
		      (not (precedence ?t1 ?t2))))	 

;;
;;NOT_CHAIN_RESPONSE(A,B) - (GLOBALLY (A -> NOT (NEXT B))) - B cannot appear immediately after A
;;

(:derived (not_chain_response ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
		  (not (traced ?t2))
	          (not (pre ?t1 ?t2))))

;;
;;NOT_CHAIN_PRECEDENCE(A,B) - A cannot appear immediately before B
;;

(:derived (not_chain_precedence ?t1 - task ?t2 - task) 		  
		  (or (not (traced ?t2))
		      (not (traced ?t1))
		      (not (pre ?t1 ?t2))))

)
