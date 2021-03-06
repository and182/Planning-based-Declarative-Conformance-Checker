;We cannot use integer or boolean parameters as inputs
;[LPG-td] - Some predicate needs to have at least an instantiation in the planning problem, otherwise it does not work.

(define (domain Mining)
(:requirements :derived-predicates :typing :equality)
(:types A B C D E F G - task) 
		
(:predicates 
(pre ?x - task ?y - task)
(traced ?x - task)
(first_task_of_the_trace ?x - task)
(last_task_of_the_trace ?x - task)

;;
;;DECLARE LTL rules
;;
(existence-B)
(absence-A)
(choice-A-B)
(ex_choice-A-B)
(res_existence-A-B)
(response-A-B)
(response ?x - task ?y - task)
;(res_existence ?x - task ?y - task)
;(precedence ?x - task ?y - task)
;(chain_response ?x - task ?y - task)
;(chain_precedence ?x - task ?y - task)
;(not_res_existence ?x - task ?y - task)
;(not_response ?x - task ?y - task)
;(not_precedence ?x - task ?y - task)
;(not_chain_response ?x - task ?y - task)
;(not_chain_precedence ?x - task ?y - task)
)

;;
;;PLANNING ACTIONS
;;

;
; If the trace is empty, the action --addToTheEmptyTrace-- adds a task 'x1' (which is actually not in the trace) to the trace. 
; After the action happening, 'x1' will be contemporaneously the first and the last task of the trace.
;
(:action addToTheEmptyTrace
:parameters (?x1 - task) 
:precondition (not (exists (?x - task) (traced ?x)))
:effect (and (traced ?x1) (first_task_of_the_trace ?x1) (last_task_of_the_trace ?x1))
)

;
; If 'x1' is the first task of the trace, the action --addAtTheBeginning-- adds a task 'x2' (which is actually not in the trace) before 'x1'. 
;

(:action addAtTheBeginning
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (not (traced ?x2)) (first_task_of_the_trace ?x1))
:effect (and (not (first_task_of_the_trace ?x1)) (first_task_of_the_trace ?x2) (traced ?x2) (pre ?x2 ?x1))
)

;
; If 'x1' is the last task of the trace, the action --addAtTheEnd-- adds a task 'x2' (which is actually not in the trace) after 'x1'.
;
(:action addAtTheEnd
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (not (traced ?x2)) (last_task_of_the_trace ?x1))
:effect (and (not (last_task_of_the_trace ?x1)) (last_task_of_the_trace ?x2) (traced ?x2) (pre ?x1 ?x2))
)

;
; The action --addBetween-- inserts the task 'x2' between the tasks 'x1' and 'x3' (that are already included in the trace).
; After the action happening, two new couples of actions will be created: '(x1,x2)' and '(x2,x3)'.
;
(:action addBetween
:parameters (?x1 - task ?x2 - task ?x3 - task) 
:precondition (and (pre ?x1 ?x3) (not (traced ?x2)))
:effect (and (traced ?x2) (pre ?x1 ?x2) (pre ?x2 ?x3) (not (pre ?x1 ?x3)))
)

;
; The action --deleteSingleTask-- deletes the task 'x1', if 'x1' it is the only task in the trace.
;
(:action deleteSingleTask
:parameters (?x1 - task) 
:precondition (and (traced ?x1) (first_task_of_the_trace ?x1) (last_task_of_the_trace ?x1))
:effect (and (not (traced ?x1)) (not (last_task_of_the_trace ?x1)) (not (first_task_of_the_trace ?x1)))
)

;
; If 'x1' is the first task of the trace, and the task 'x2' follows 'x1', the action --deleteAtTheBeginning-- deletes 'x1' and makes 'x2' the new first task of the trace.
;
(:action deleteAtTheBeginning
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (traced ?x2) (pre ?x1 ?x2) (first_task_of_the_trace ?x1))
:effect (and (not (traced ?x1)) (not (first_task_of_the_trace ?x1)) (first_task_of_the_trace ?x2) (not (pre ?x1 ?x2)))
)

;
; If 'x2' is the last task of the trace, and the task 'x2' follows 'x1', the action --deleteAtTheEnd-- deletes 'x2' and makes 'x1' the new last task of the trace.
;
(:action deleteAtTheEnd
:parameters (?x1 - task ?x2 - task) 
:precondition (and (traced ?x1) (traced ?x2) (pre ?x1 ?x2) (last_task_of_the_trace ?x2))
:effect (and (not (traced ?x2)) (not (last_task_of_the_trace ?x2)) (last_task_of_the_trace ?x1) (not (pre ?x1 ?x2)))
)

;
; The action --deleteBetween-- deletes the task x2 that was previously situated between the tasks x1 and x3.
;
(:action deleteBetween
:parameters (?x1 - task ?x2 - task ?x3 - task)
:precondition (and (traced ?x1) (traced ?x2) (traced ?x3) (pre ?x1 ?x2) (pre ?x2 ?x3))
:effect (and (not (traced ?x2)) (not (pre ?x1 ?x2)) (not (pre ?x2 ?x3)) (pre ?x1 ?x3))
)

;;;;;;;
;;DERIVED PREDICATES
;;;;;;;

;;
;;EXISTENCE-B - (FINALLY B) - An instance of the task B must occur at least once in the trace.
;;

(:derived (existence-B) 
		  (exists (?ta - B) (traced ?ta)))

;;
;;ABSENCE-A - (NOT (FINALLY A)) - A never occur in the trace.
;;

(:derived (absence-A) 
		  (not (exists (?ta - A) (traced ?ta))))

;;
;;CHOICE-A-B - (FINALLY A OR FINALLY B) - A or B occur eventually in the trace. The formula is TRUE also if they both occur in the trace.
;;

(:derived (choice-A-B) (or (exists (?ta - A) (traced ?ta))
						   (exists (?tb - B) (traced ?tb))))
						   
;;
;; EXCLUSIVE_CHOICE-A-B - (FINALLY A OR FINALLY B) AND (NOT (FINALLY A AND FINALLY B)) 
;; Only A or only B can occur eventually in the trace (but not both).
;;

(:derived (ex_choice-A-B) (and (exists (?ta - A ?tb - B) (or (traced ?ta) (traced ?tb)))
							   (not (exists (?ta - A ?tb - B) (and (traced ?ta) (traced ?tb))))))  
							   
;;
;; RESPONDED_EXISTENCE-A-B - (FINALLY A -> FINALLY B) - If an instance of A occurs in the trace, then an instance of B must occur 
;; in the trace as well (before or after A, the order here is not important).
;;

(:derived (res_existence-A-B) 
	      (forall (?ta - A) (or (not (traced ?ta)) 
							    (and (traced ?ta) (exists (?tb - B) (traced ?tb))))))

;;
;;RESPONSE-A-B - (GLOBALLY (A -> FINALLY B)) - When an instance of A occurs in the trace, an instance of B should eventually occur after A.
;;

(:derived (response-A-B) 
		  (forall (?ta - A) 
				  (exists (?tb - B) (response ?ta ?tb))))
				  
(:derived (response ?t1 - task ?t2 - task) 
	      (or (not (traced ?t1))
	          (pre ?t1 ?t2)
	          (exists (?t3 - task) (and (pre ?t1 ?t3) (response ?t3 ?t2)))))


)
